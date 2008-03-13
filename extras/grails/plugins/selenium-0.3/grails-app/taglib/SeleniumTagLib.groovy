class SeleniumTagLib {
    static namespace = "sel"

    def test = { attrs, body ->
        out << body()
        out << render(contextPath:pluginContextPath, template:'/selenium/testTemplate', model:[name:attrs.name?attrs.name:getName(request.uri.toString()), rows:getList(request)])
    }

    def row = { attrs ->
        if(!(attrs.line || attrs.command))
            throwTagError("Tag must have attribute 'line' or 'command','target' and 'value'")

        def list = getList(request)
        if(attrs.line) {
            list.add(attrs.line)
        } else {
            list.add("${attrs.command}|${attrs.target?attrs.target:''}|${attrs.value?attrs.value:''}")
        }
    }

    private def getList(def request) {
        def list = request.getAttribute('list')
        if(list==null) {
            list = []
            request.setAttribute('list', list)
        }
        return list
    }

    private String getName(String uri) {
        String name = uri.substring(uri.lastIndexOf('/')+1)
        if(name.indexOf('?')>-1) {
            name = name.substring(0,name.indexOf('?'))
        }
        return name
    }
}
