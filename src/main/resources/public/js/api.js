function loadGetLoginMsg() {
    let nameVar = document.getElementById("user").value;
    let passwdVar = document.getElementById("passwd").value;
    if (nameVar.trim() === "" || passwdVar.trim() === "") {
        alert("El campo no puede estar vacío.");
        return;
    }
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function() {
        if (this.status === 200) {
            console.log(this.responseText);
            document.getElementById("responseContainer").innerHTML = this.responseText;
        } else if (this.status === 401) {
            alert("Credenciales incorrectas. Por favor, inténtalo de nuevo.");
            document.getElementById("responseContainer").innerHTML = "";
        } else {
            alert("Error desconocido. Por favor, inténtalo de nuevo más tarde.");
        }
    };
    xhttp.onerror = function() {
        alert("Error de red. Por favor, inténtalo de nuevo más tarde.");
    };
    xhttp.open("GET", "/loginservice?user=" + encodeURIComponent(nameVar) + "&passwd=" + encodeURIComponent(passwdVar));
    xhttp.send();
}