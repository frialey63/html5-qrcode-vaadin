package org.vaadin.example.addon;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.vaadin.addon.html5qrcode.Html5Qrcode;
import org.vaadin.addon.html5qrcode.Html5Qrcode.Option;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class Html5QrcodeView extends VerticalLayout {

    private static final long serialVersionUID = -4302145178663286253L;

    private final static Logger LOGGER = Logger.getLogger(Html5QrcodeView.class.getName());

    public Html5QrcodeView() {
        super();

        Map<String, Object> options = new HashMap<>();

        options.put(Option.fps.name(), 10);
        options.put(Option.qrbox.name(), 250);
        options.put(Option.supportedScanTypes.name(), "[Html5QrcodeScanType.SCAN_TYPE_CAMERA]");

        Html5Qrcode component = new Html5Qrcode(sc -> {
            LOGGER.info("uuid = " + sc.getValue());
        }, options);

        component.setWidthFull();
        component.setHeightFull();

        setHorizontalComponentAlignment(Alignment.CENTER, component);

        add(component);

        setSizeFull();
    }
}
