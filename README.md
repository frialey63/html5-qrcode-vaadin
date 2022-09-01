# Vaadin HTML5 QR Code Add-on

This is a Vaadin Java component which wraps the html5-qrcode Javascript library derived from the project at:

[html5-qrcode](https://github.com/mebjas/html5-qrcode)

Refer to the following blog for a full description:

[HTML5 QR Code scanner](https://blog.minhazav.dev/research/html5-qrcode)

This add-on was developed because the existing QR code add-ons in the Vaadin directory (e.g ZXingVaadin) were found to be non-functional under Vaadin 23.

Some effort was expended in attempting to package it in the form of a Lit Template in conjunction with an npm package, as a more natural package format.  However, this failed due to the scanner not initialising in the view although it appeared to be present in the page (confirmed using Chrome Developer tools).  The problem seemed to be similar to those encountered while testing other Vaadin QR add-ons.

I hope this component is of use to other Vaadin developers.  Note I have only fully evaluated this on the following configuration:

    Vaadin 23.0.7, Java 17 and SpringBoot 2.6.7

and no guarantees (or support) can be offered for other configurations.