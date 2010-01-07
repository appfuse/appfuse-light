function highlightTableRows(tableId) {
    var previousClass = null;
    var table = document.getElementById(tableId);
    var startRow = 0;
    // workaround for Tapestry not using thead
    if (!table.getElementsByTagName("thead")[0]) {
	    startRow = 1;
    }
    var tbody = table.getElementsByTagName("tbody")[0];
    var rows = tbody.getElementsByTagName("tr");
    // add event handlers so rows light up and are clickable
    for (i=startRow; i < rows.length; i++) {
        rows[i].onmouseover = function() { previousClass=this.className;this.className+=' over' };
        rows[i].onmouseout = function() { this.className=previousClass };
        rows[i].onclick = function() {
            var cell = this.getElementsByTagName("td")[0];
            var link = cell.getElementsByTagName("a")[0];
            if (link.hasAttribute("onclick")) {
                call = link.getAttribute("onclick");
                if (call.indexOf("return ") == 0) {
                    call = call.substring(7);
                }
                // this will not work for links with onclick handlers that return false
                eval(call);
            } else {
                get(link.getAttribute("href"));
            }
            this.style.cursor="wait";
            return false;
        }
    }
}

// Show the document's title on the status bar
window.defaultStatus=document.title;

// History support for Ajaxified Body: http://raibledesigns.com/rd/entry/ajaxified_body
window.dhtmlHistory.create({
    toJSON: function(o) {
        return Object.toJSON(o);
    },
    fromJSON: function(s) {
        return s.evalJSON();
    }
});

var historyListener = function(newLocation, historyData) {
    // if there is no location then display the default
   if (newLocation == "") {
      newLocation = "";
   }
   get(newLocation);
}

// The following doesn't work in IE 6 or 7
// window.onload = StyleSheetSwitcher.initialize();
window.onload = function() {
    StyleSheetSwitcher.initialize();
    ajaxifyURLs('body');
    ajaxifyURLs('nav');
    dhtmlHistory.initialize();
    dhtmlHistory.addListener(historyListener);
    if (dhtmlHistory.isFirstLoad()) {
        if (location.hash) {
            url = location.hash.substring(1);
            get(url);
        }
    }
}

function get(url) {
    // if hostname in URL, assume is absolute and strip protocol/server/port
    if (url.indexOf("http") > -1 && url.indexOf(document.location.hostname) > -1) {
        if (document.location.port == "") {
            url = url.substring(url.indexOf(document.location.hostname) + document.location.hostname.length);
        } else {
            url = url.substring(url.indexOf(document.location.port) + document.location.port.length);
        }
        if (url.indexOf(document.location.pathname) > -1) {
            url = url.substring(url.indexOf(document.location.pathname) + document.location.pathname.length);
        }
    }
    dhtmlHistory.add(url);
    //alert(dhtmlHistory.get)
    //$('ajaxLoading').appear({duration: .1});
    new Effect.Appear('ajaxLoading', { duration: 0 });
    new Ajax.Request(url + ((url.indexOf('?') > -1) ? "&" : "?") + "ajax=true", {
        method: 'get',
        onComplete: function(response) {
            var  text = response.responseText;
            if (200 == response.status) {
                // if response contains title, replace existing title
                if (text.indexOf("<title>") > -1) {
                    var title = text.substring(text.indexOf("<title>") + 7, text.indexOf(("</title>")));
                    document.title = title + " " + document.title.substring(document.title.indexOf("|"));
                    $('main').getElementsByTagName("h1")[0].innerHTML = title;
                } else {
                    $('main').getElementsByTagName("h1")[0].innerHTML = "";
                    document.title = "Welcome " + document.title.substring(document.title.indexOf("|"));
                }

                // if response contains head, append to current head
                if (text.indexOf("<head>") > -1) {
                    head = text.substring(text.indexOf("<head>") + 6, text.indexOf(("</head>")));
                    document.getElementsByTagName("head")[0].innerHTML += head;
                    // eval any new scripts in head
                    // TODO: Figure out why the following causes the "Add User" button not to work
                    //evalJS(document.getElementsByTagName("head")[0]);
                }

                $('body').innerHTML = response.responseText;

                // if response contains <script>, eval it
                evalJS($('body'));
                ajaxifyURLs('body');
            } else {
                // rather than alerting the error messages, just go to the page and show error page from server
                location.href = url;
            }
        }

    });
    new Effect.Fade('ajaxLoading', {duration: 1});
    $('ajaxLoading').style.display = "none";
}

function ajaxifyURLs(domId) {
    var ahrefs = $(domId).getElementsByTagName("a");
    for (i=0; i < ahrefs.length; i++) {
        if ((ahrefs[i].getAttribute("href").indexOf("http") == -1) && !ahrefs[i].onclick) {
            // don't decorate exportlinks for displaytag
            if (!ahrefs[i].parentNode.hasClassName("exportlinks")) {
                ahrefs[i].onclick = function() {
                    //this.style.cursor='wait';
                    get(this.href);
                    return false;
                }
            }
        }
    }

    // look for buttons with onclick='location.href='
    var buttons = $(domId).getElementsByTagName("button");
    for (i=0; i < buttons.length; i++) {
        if (buttons[i].hasAttribute("onclick") && buttons[i].getAttribute("onclick").indexOf('location.href') > -1) {
            var location = buttons[i].getAttribute("onclick");
            location = location.substring(location.indexOf(".href") + 7, location.length-1);
            //alert(location);
            buttons[i].onclick = function() {
                //this.style.cursor = 'wait';
                get(location);
            };
        }
    }
}

function evalJS(element) {
    var scripts = element.getElementsByTagName("script");
    for (i=0; i < scripts.length; i++) {
        // if src, eval it, otherwise eval the body
        if (scripts[i].hasAttribute("src")) {
            eval(scripts[i].getAttribute("src"));
        } else {
            //alert(scripts[i].innerHTML)
            eval(scripts[i].innerHTML);
        }
    }
}
