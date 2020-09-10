package com.odoo.addons.expeditions.providers;

import com.odoo.base.addons.invoice.InvoiceDelivery;
import com.odoo.core.orm.provider.BaseModelProvider;

public class ExpeditionSyncProvider extends BaseModelProvider {
    public static final String TAG= ExpeditionSyncProvider.class.getSimpleName();


    @Override
    public String authority(){
        return InvoiceDelivery.AUTHORITY;
    }
}
