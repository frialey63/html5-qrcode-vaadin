package org.vaadin.addons.pjp.html5qrcode;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;
import org.vaadin.addons.pjp.html5qrcode.Html5Qrcode.Option;

/**
 * Test cases for Html5Qrcode in JUnit4.
 * @author Paul
 *
 */
public class Html5QrcodeTest {

    @Test
    public void testFormatOptionsEmpty() {
        Map<String, Object> options = new TreeMap<>();

        assertEquals("", Html5Qrcode.formatOptions(options));
    }

    @Test
    public void testFormatOptions() {
        Map<String, Object> options = new TreeMap<>();

        options.put(Option.fps.name(), 10);
        options.put(Option.qrbox.name(), 250);
        options.put(Option.supportedScanTypes.name(), "[Html5QrcodeScanType.SCAN_TYPE_CAMERA]");

        assertEquals("fps: 10, qrbox: 250, supportedScanTypes: [Html5QrcodeScanType.SCAN_TYPE_CAMERA]", Html5Qrcode.formatOptions(options));
    }

}
