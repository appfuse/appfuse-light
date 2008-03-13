class SeleniumControllerTest extends GroovyTestCase {
    void testSuite() {
        def controller = new SeleniumController()
        controller.suite()
        def model = controller.modelAndView.model
        
        assert model
        assertEquals (['test0a.html', 'test0b.psv', 'test0c.psv', 'tst1/test1a.psv', 'tst1/tst2/test2a.psv', 'tst3/test3a.psv'] , model.tests)
        assertEquals (['tst1','tst1/tst2','tst3'] , model.suites)
    }

    void testSuiteWithParam() {
        def controller = new SeleniumController()
        controller.params.dir='tst1'
        controller.suite()
        def model = controller.modelAndView.model
        
        assert model
        assertEquals (['tst1/test1a.psv'] , model.tests)
        assertEquals (['tst1','tst1/tst2','tst3'] , model.suites)
    }

}
