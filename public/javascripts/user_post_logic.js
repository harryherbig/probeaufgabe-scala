var HttpClient = function() {
    this.get = function(aUrl, aCallback) {
        var anHttpRequest = new XMLHttpRequest();
        anHttpRequest.onreadystatechange = function() {
            if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)
                aCallback(anHttpRequest.responseText);
        }

        anHttpRequest.open( "GET", aUrl, true );
        anHttpRequest.send( null );
    }
}

var client = new HttpClient();

function search() {
    var selectedUser = document.getElementById("userSelection").value;
    client.get("/user/" + selectedUser, function(response) {
        document.getElementById("userRoom").innerHTML = response
    });
}