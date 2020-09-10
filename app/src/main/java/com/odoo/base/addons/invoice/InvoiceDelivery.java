package com.odoo.base.addons.invoice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.odoo.BuildConfig;
import com.odoo.core.orm.OModel;
import com.odoo.core.orm.fields.OColumn;
import com.odoo.core.orm.fields.types.ODate;
import com.odoo.core.orm.fields.types.ODateTime;
import com.odoo.core.orm.fields.types.OInteger;
import com.odoo.core.orm.fields.types.OSelection;
import com.odoo.core.orm.fields.types.OVarchar;
import com.odoo.core.support.OUser;

public class InvoiceDelivery extends OModel {
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID+ ".core.provider.content.sync.invoice_delivery";

    OColumn name = new OColumn("No LE", OVarchar.class).setSize(100).setRequired();
    OColumn personal_code = new OColumn("Kode Personil", OVarchar.class).setSize(20);
    OColumn delivery_date = new OColumn("Tanggal", ODate.class);
    OColumn odometer_start = new OColumn("KM Berangkat", OInteger.class).setLocalColumn();
    OColumn odometer_finish = new OColumn("KM Kembali", OInteger.class).setLocalColumn();
    OColumn vehicle_number = new OColumn("No TNKB", OVarchar.class).setSize(20);
    OColumn time_receive = new OColumn("Waktu Terima", ODateTime.class);
    OColumn time_go = new OColumn("Waktu Berangkat", ODateTime.class);
    OColumn time_back = new OColumn("WaktuKembali", ODateTime.class);

    OColumn state_deliver = new OColumn("Status", OSelection.class)
            .addSelection("cancel","Batal")
            .addSelection("pending","Pending")
            .addSelection("sent","Kirim")
            .addSelection("receive","Terima")
            .addSelection("back","Kembali");


    public InvoiceDelivery(Context context,  OUser user) {
        super(context, "invoice.delivery", user);
        setHasMailChatter(true);
    }

    @Override
    public Uri uri(){
        return buildURI(AUTHORITY);
    }

    @Override
    public void onModelUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
