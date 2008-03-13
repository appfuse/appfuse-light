class SeleniumHelperTest extends GroovyTestCase {
    void testFileToLines() {
        def helper = new SeleniumHelper(new File('.'))
        File file = new File('./test/unit/resources/sampleFile1.txt')
        assertEquals(['a b c','d e f '], helper.fileToLines(file))
    }

    void testValidateFile() {
        def helper = new SeleniumHelper(new File('.'))
        // inside base dir
        helper.validateFile(new File('./x'));

        // outside base dir
        try {
            helper.validateFile(new File('../x'));
            fail('exception should have been thrown');
        } catch (RuntimeException e) {
        }
    }

    void testAcceptDirectory() {
        def helper = new SeleniumHelper(new File('.'))
        def hiddenDir = new File('./test/unit/resources/.myhiddendir')
        hiddenDir.mkdir()
        assertFalse(helper.acceptDirectory(hiddenDir))
        assertTrue(helper.acceptDirectory(new File('./test/unit/resources')))
    }
}