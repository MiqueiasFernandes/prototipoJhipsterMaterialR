var tableRef = document.getElementById('terminal').getElementsByTagName('tbody')[0];
var inserttext = document.getElementById('insert-text');
var alert = document.getElementById("alert");
var type = document.getElementById("type");
var prompt = document.getElementById("prompt");
var linhaTemporaria = document.getElementById("linha-temporaria");
var comandos = [];
var socket;


$("#type").keydown(function (e) {
    var code = e.keyCode || e.which;
    if (code === 13 && websocket) {//enter
        websocket.send(type.value + "\n");
        log("enviado: " + type.value);
        comandos[comandos.length] = type.value;
    }

    if (code === 38 && comandos.length > 0) {
        var index = $.inArray(type.value, comandos);
        if (index > -1) {
            type.value = comandos[index > 0 ? index - 1 : 0];
        } else {
            type.value = comandos[comandos.length - 1];
        }
    }

    if (code === 40 && comandos.length > 0) {
        var index = $.inArray(type.value, comandos);
        if (index > -1 && ++index < comandos.length) {
            type.value = comandos[index];
        }
    }
});


var lineCount = 0;


function insertLine(text, classe) {
    var newCell = tableRef.insertRow(tableRef.rows.length - 2).insertCell(0);
    newCell.className = classe;
    newCell.appendChild(document.createTextNode(text));
    newCell.id = 'line-' + lineCount++;
    if (lineCount > 20) {
        document.getElementById("line-" + (lineCount - 20)).className += " linha-hiden";
    }
}



var handlers = [], msgs = [];


handlers["echo"] = function (msg) {
    type.value = "";
    insertLine(msg.data, "linha linha-input");
    type.focus();
};
handlers["comando"] = function (msg) {
    msg = msg.data;
    if (msg.indexOf("\n") >= 0) {
        insertLine(linhaTemporaria.innerText + msg, "linha");
        linhaTemporaria.innerText = "";
        log("\"" + msg + "\" tratado cm nova linha");
    } else {
        linhaTemporaria.innerText += msg;
        type.focus();
        log(msg + " tratado cm simples");
    }
    type.focus();
};
handlers["prompt"] = function (msg) {
    prompt.innerText = msg.data;
    type.focus();
};
handlers["alerta"] = function (msg) {
    log("alerta recebido: " + msg.data);
};
handlers["file"] = function (msg) {
    log("arquivo recebido: " + msg.data);
};


msgs["echo"] = function (msg) {
    log("[echo]" + ": " + msg.data);
};
msgs["comando"] = function (msg) {
    log("[comando]" + ": " + msg.data);
};
msgs["prompt"] = function (msg) {
    log("[prompt]" + ": " + msg.data);
};
msgs["alerta"] = function (msg) {
    log("[alerta]" + ": " + msg.data);
};
msgs["file"] = function (msg) {
    log("[file]" + ": " + msg.data);
};

var int = 675;

function getToken() {
    return "tokenProj" + int;
    //return document.getElementById("token").value + int;
}

var local = null;


function initServers(servers) {
    var url = "ws://" + local + "/prototiposervice/rlive/" + (getToken()) + "/";
    for (var server in servers) {
        server = servers[server];
        log("tentando abrir conexão em " + url + server);

        var webs = new WebSocket(url + server);

        webs.onopen = msgs[server]("open");
        webs.onerror = msgs[server]("close");
        webs.onmessage = handlers[server];

        if (server === "comando")
            websocket = webs;

    }
}


function initrLive() {
    local = type.value;
    if (!local) {
        alertar("local nao esta definido! \ninsira-o no prompt ip:porta");
        $('#realtime').prop("checked", false);
        return;
    }
    log('iniciando webservice rlive... para ' + "http://" + local + "/prototiposervice/webresources/generic");
    $.ajax({
        type: "get",
        url: "http://" + local + "/prototiposervice/webresources/generic",
        data: {
            token: getToken()
        },
        dataType: "json",
        success: function (data) {
            log("json received: " + JSON.stringify(data));
            initServers(data.servers);
            int++;
        },
        error: function (event, jqxhr, settings, thrownError) {
            log("error " + (jqxhr));
             $('#realtime').prop("checked", false);
        }
    });
}



function log(text) {
    alert.innerHTML += text + "<br/>";
}


function alertar(msg){

swal(msg);
}


function send() {

    if (!local) {
        alertar("local nao esta definido! \ninsira-o no prompt ip:porta");
         $('#realtime').prop("checked", false);
        return;
    }


    var script = {
        token: getToken(),
        script: btoa($("#script").val())
    };

    log('sending.. ' + JSON.stringify(script));

    $.ajax({
        url: "http://" + local + "/prototiposervice/webresources/generic",
        type: 'post',
        contentType: "text/plain",
        data: (JSON.stringify(script)),
        dataType: "text",
        success: function (data) {
            var res = atob(data);
            document.getElementById("saida").innerHTML = toHTML(res);
            log("received post: " + res);
        }
    });
}

function b64EncodeUnicode(str) {
    return btoa(encodeURIComponent(str).replace(/%([0-9A-F]{2})/g, function (match, p1) {
        return String.fromCharCode('0x' + p1);
    }));
}

function toHTML(data) {
    while (data.indexOf("\n") >= 0) {
        data = data.replace("\n", "<br/>");
    }
    return data;
}

