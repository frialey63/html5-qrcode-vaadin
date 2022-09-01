package org.vaadin.addon.html5qrcode;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;

/**
 * This simple add-on wraps the HTML5 QR Code scanner.
 * @see <a href="https://github.com/mebjas/html5-qrcode">Html5Qrcode</a>
 * @author Paul
 *
 */
@CssImport("./my-styles/html5-qrcode.css")
@JavaScript("https://unpkg.com/html5-qrcode")
public class Html5Qrcode extends Div {

    /**
     * The enum which defines the options for the scanner, see project website for the definitive reference.
     * @author Paul
     *
     */
    public enum Option {
          /**
           * Array of formats to support of type {@type Html5QrcodeSupportedFormats}.
           */
        formatsToSupport,
          /**
           * If true, all logs would be printed to console. False by default.
           */
        verbose,
          /**
           * Optional, Expected framerate of QR code scanning. example { fps: 2 } means the
           * scanning would be done every 500 ms.
           */
        fps,
          /**
           * Optional, edge size, dimension or calculator function for QR scanning
           * box, the value or computed value should be smaller than the width and
           * height of the full region.
           *
           * This would make the scanner look like this:
           *          ----------------------
           *          |********************|
           *          |******,,,,,,,,,*****|      <--- shaded region
           *          |******|       |*****|      <--- non shaded region would be
           *          |******|       |*****|          used for QR code scanning.
           *          |******|_______|*****|
           *          |********************|
           *          |********************|
           *          ----------------------
           *
           * Instance of {@interface QrDimensions} can be passed to construct a non
           * square rendering of scanner box. You can also pass in a function of type
           * {@type QrDimensionFunction} that takes in the width and height of the
           * video stream and return QR box size of type {@interface QrDimensions}.
           *
           * If this value is not set, no shaded QR box will be rendered and the scanner
           * will scan the entire area of video stream.
           */
        qrbox,
          /**
           * Optional, Desired aspect ratio for the video feed. Ideal aspect ratios
           * are 4:3 or 16:9. Passing very wrong aspect ratio could lead to video feed
           * not showing up.
           */
        aspectRatio,
          /**
           * Optional, if {@code true} flipped QR Code won't be scanned. Only use this
           * if you are sure the camera cannot give mirrored feed if you are facing
           * performance constraints.
           */
        disableFlip,
          /**
           * Optional, @beta(this config is not well supported yet).
           *
           * Important: When passed this will override other parameters like
           * 'cameraIdOrConfig' or configurations like 'aspectRatio'.
           * 'videoConstraints' should be of type {@code MediaTrackConstraints} as
           * defined in
           * https://developer.mozilla.org/en-US/docs/Web/API/MediaTrackConstraints
           * and is used to specify a variety of video or camera controls like:
           * aspectRatio, facingMode, frameRate, etc.
           */
        videoConstraints,
          /**
           * If {@code true} the library will remember if the camera permissions
           * were previously granted and what camera was last used. If the permissions
           * is already granted for "camera", QR code scanning will automatically
           * start for previously used camera.
           */
        rememberLastUsedCamera,
          /**
           * Sets the desired scan types to be supported in the scanner.
           *
           *  - Not setting a value will follow the default order supported by
           *      library.
           *  - First value would be used as the default value. Example:
           *    - [SCAN_TYPE_CAMERA, SCAN_TYPE_FILE]: Camera will be default type,
           *      user can switch to file based scan.
           *    - [SCAN_TYPE_FILE, SCAN_TYPE_CAMERA]: File based scan will be default
           *      type, user can switch to camera based scan.
           *  - Setting only value will disable option to switch to other. Example:
           *    - [SCAN_TYPE_CAMERA] - Only camera based scan supported.
           *    - [SCAN_TYPE_FILE] - Only file based scan supported.
           *  - Setting wrong values or multiple values will fail.
           */
        supportedScanTypes
    }

    /**
     * The interface which defines the callback through which the scan value is obtained.
     * @author Paul
     *
     */
    @FunctionalInterface
    public interface Scanning {
        String getValue();
    }

    private static final long serialVersionUID = -2678129358775527301L;

    static String formatOptions(Map<String, Object> options) {
        StringBuffer sb = new StringBuffer();

        final String sep = ", ";

        for (Entry<String, Object> entry : options.entrySet()) {
            sb.append(entry.getKey());
            sb.append(": ");
            sb.append(entry.getValue());
            sb.append(sep);
        }

        String str = sb.toString();

        if (str.endsWith(sep)) {
            int len = str.length();
            str = str.substring(0, (len - 2));
        }

        return str;
    }

    private final String javascript =
            "function onScanSuccess(decodedText, decodedResult) {" +
            "    $0.$server.myScanSuccess(decodedText); html5QrcodeScanner.clear();" +
            "};" +

            "var html5QrcodeScanner = new Html5QrcodeScanner('reader', { %s });" +
            "html5QrcodeScanner.render(onScanSuccess);"
            ;

    private final Consumer<Scanning> scanConsumer;

    /**
     * @param scanConsumer The callback as a Java consumer
     * @param options The map of scanner options
     */
    public Html5Qrcode(Consumer<Scanning> scanConsumer, Map<String, Object> options) {
        super();
        this.scanConsumer = scanConsumer;

        setId("reader");

        getElement().executeJs(String.format(javascript, formatOptions(options)), this.getElement());
    }

    @ClientCallable
    private void myScanSuccess(String value){
        scanConsumer.accept(new Scanning() {

            @Override
            public String getValue() {
                return value;
            }
        });
    }
}
