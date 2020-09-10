package com.odoo.addons.expeditions.services;

import android.content.Context;
import android.os.Bundle;

import com.odoo.base.addons.invoice.InvoiceDelivery;
import com.odoo.core.service.OSyncAdapter;
import com.odoo.core.service.OSyncService;
import com.odoo.core.support.OUser;

public class ExpeditionSyncService  extends OSyncService {
    @Override
    public OSyncAdapter getSyncAdapter(OSyncService service, Context context) {
        return new OSyncAdapter(context, InvoiceDelivery.class, service, true);
    }

    @Override
    public void performDataSync(OSyncAdapter adapter, Bundle extras, OUser user) {
        adapter.syncDataLimit(80);
    }
}
