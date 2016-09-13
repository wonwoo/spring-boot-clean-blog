/**
 * Created by wonwoo on 2016. 9. 13..
 */
function changeCategory(name){
    document.getElementById("categoryName").value = name;
}

var URL = window.URL || window.webkitURL || window.mozURL || window.msURL;
navigator.saveBlob = navigator.saveBlob || navigator.msSaveBlob || navigator.mozSaveBlob || navigator.webkitSaveBlob;
window.saveAs = window.saveAs || window.webkitSaveAs || window.mozSaveAs || window.msSaveAs;

// Because highlight.js is a bit awkward at times
var languageOverrides = {
    js: 'javascript',
    html: 'xml'
};

var md = markdownit({
    html: true,
    highlight: function (code, lang) {
        if (languageOverrides[lang]) lang = languageOverrides[lang];
        if (lang && hljs.getLanguage(lang)) {
            try {
                return hljs.highlight(lang, code).value;
            } catch (e) {
            }
        }
        return '';
    }
}).use(markdownitFootnote);


var hashto;

function update(e) {
    setOutput(e.getValue());

    clearTimeout(hashto);
    hashto = setTimeout(updateHash, 1000);
}

function setOutput(val) {
    val = val.replace(/<equation>((.*?\n)*?.*?)<\/equation>/ig, function (a, b) {
        return '<img src="http://latex.codecogs.com/png.latex?' + encodeURIComponent(b) + '" />';
    });

    var out = document.getElementById('out');
    var old = out.cloneNode(true);
    out.innerHTML = md.render(val);

    var allold = old.getElementsByTagName("*");
    if (allold === undefined) return;

    var allnew = out.getElementsByTagName("*");
    if (allnew === undefined) return;

    for (var i = 0, max = Math.min(allold.length, allnew.length); i < max; i++) {
        if (!allold[i].isEqualNode(allnew[i])) {
            out.scrollTop = allnew[i].offsetTop;
            return;
        }
    }
}

var editor = CodeMirror.fromTextArea(document.getElementById('code'), {
    mode: 'gfm',
    lineNumbers: true,
    matchBrackets: true,
    lineWrapping: true,
    theme: 'base16-light',
    extraKeys: {"Enter": "newlineAndIndentContinueMarkdownList"}
});

editor.on('change', update);


function saveAsHtml() {
    document.getElementById('content').value = document.getElementById('out').innerHTML;
    document.getElementById('post').submit();
}

function updateHash() {
    window.location.hash = btoa( // base64 so url-safe
        RawDeflate.deflate( // gzip
            unescape(encodeURIComponent( // convert to utf8
                editor.getValue()
            ))
        )
    );
}

if (window.location.hash) {
    var h = window.location.hash.replace(/^#/, '');
    if (h.slice(0, 5) == 'view:') {
        setOutput(decodeURIComponent(escape(RawDeflate.inflate(atob(h.slice(5))))));
        document.body.className = 'view';
    } else {
        editor.setValue(
            decodeURIComponent(escape(
                RawDeflate.inflate(
                    atob(
                        h
                    )
                )
            ))
        );
        update(editor);
        editor.focus();
    }
} else {
    update(editor);
    editor.focus();
}