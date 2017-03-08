<?php
require_once('../simpletest/reporter.php');

class MonRapporteurHTML extends HtmlReporter {

    /*
    function paintHeader($test_name) {
    }

    function paintFooter($test_name) {
    }

    function paintStart($test_name, $size) {
        parent::paintStart($test_name, $size);
    }

    function paintEnd($test_name, $size) {
        parent::paintEnd($test_name, $size);
    }

    function paintFail($message) {
        parent::paintFail($message);
    }
    */

    function paintPass($message) {
        parent::paintPass($message);
        print "<span class=\"pass\">Pass</span>: ";
        $breadcrumb = $this->getTestList();
        array_shift($breadcrumb);
        print implode("-&gt;", $breadcrumb);
        print "-&gt; $message <br>\n";
    }

    protected function getCss() {
        return parent::getCss() . ' .pass { color: green; }';
    }
}
?>
