package com.odoo.addons.expeditions;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.odoo.R;
import com.odoo.base.addons.invoice.InvoiceDelivery;
import com.odoo.core.orm.ODataRow;
import com.odoo.core.support.addons.fragment.BaseFragment;
import com.odoo.core.support.addons.fragment.ISyncStatusObserverListener;
import com.odoo.core.support.drawer.ODrawerItem;
import com.odoo.core.support.list.OCursorListAdapter;
import com.odoo.core.utils.OControls;


import java.util.ArrayList;
import java.util.List;

public class Expeditions extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>,
        ISyncStatusObserverListener, SwipeRefreshLayout.OnRefreshListener, OCursorListAdapter.OnViewBindListener {

    public static final String KEY = Expeditions.class.getSimpleName();
    private View mView;
    private ListView listView;
    private OCursorListAdapter listAdapter;

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.common_listview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        listView = (ListView) view.findViewById(R.id.listview);
        listAdapter = new OCursorListAdapter(getActivity(),null, R.layout.expedition_headers);
        listView.setAdapter(listAdapter);

        listAdapter.setOnViewBindListener(this);

        setHasSyncStatusObserver(KEY, this, db());
        getLoaderManager().initLoader(0,null, this);
    }

    @Override
    public void  onViewBind(View view, Cursor cursor, ODataRow row){
        OControls.setText(view, R.id.name, row.getString("name"));
    }

    @Override
    public void onStatusChange(Boolean changed){
        if(changed){
            getLoaderManager().restartLoader(0, null, this);
        }
    }

    @Override
    public List<ODrawerItem> drawerMenus(Context context) {
        List<ODrawerItem> items = new ArrayList<>();
        items.add(new ODrawerItem(KEY).setTitle("Expedition")
                .setIcon(R.drawable.ic_action_github)
                .setInstance(new Expeditions()));
        return items;
    }

    @Override
    public  Class<InvoiceDelivery> database() {
        return InvoiceDelivery.class;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), db().uri(), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        listAdapter.changeCursor(data);
        if(data.getCount()>0){
            OControls.setGone(mView, R.id.loadingProgress);
            OControls.setVisible(mView, R.id.swipe_container);
            OControls.setGone(mView, R.id.data_list_no_item);
        }else {
            OControls.setGone(mView, R.id.loadingProgress);
            OControls.setGone(mView, R.id.swipe_container);
            OControls.setVisible(mView, R.id.data_list_no_item);
            OControls.setText(mView, R.id.title, _s(R.string.label_no_customer_found));
            OControls.setText(mView, R.id.subTitle, "Swipe to update");
        }
        if(db().isEmptyTable()){
            onRefresh();
        }
    }

    @Override
    public void onRefresh(){
        if (inNetwork()){
            parent().sync().requestSync(InvoiceDelivery.AUTHORITY);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listAdapter.changeCursor(null);
    }
}
