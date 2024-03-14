function loadGetLoginMsg() {
    let nameVar = document.getElementById("user").value;
    let passwdVar = document.getElementById("passwd").value;
    if (nameVar.trim() === "" || passwdVar.trim() === "" ) {
        alert("El campo no puede estar vacío.");
        return;
    }
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function() {
        console.log(this.responseText);
    };
    xhttp.open("GET", "/loginservice?user=" + nameVar + "&passwd=" + passwdVar);
    xhttp.send();
}

