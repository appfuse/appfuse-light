function highlightTableRows(tableId) {
    var previousClass = null;
    var table = document.getElementById(tableId); 
    var tbody = table.getElementsByTagName("tbody")[0];
    var rows = tbody.getElementsByTagName("tr");
    // add event handlers so rows light up and are clickable
    for (i=0; i < rows.length; i++) {
        rows[i].onmouseover = function() { previousClass=this.className;this.className+=' over' };
        rows[i].onmouseout = function() { this.className=previousClass };
        rows[i].onclick = function() {
            var cell = this.getElementsByTagName("td")[0];
            if (cell.getElementsByTagName("a").length > 0) {
                var link = cell.getElementsByTagName("a")[0];
                if (link.onclick) {
                    call = link.getAttributeValue("onclick");
                    // this will not work for links with onclick handlers that return false
                    eval(call);
                } else {
                  location.href = link.getAttribute("href");
                }
                this.style.cursor="wait";
            }
        }
    }
}

// Show the document's title on the status bar
window.defaultStatus=document.title;