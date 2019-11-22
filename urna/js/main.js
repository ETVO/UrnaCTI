function hasNumber(myString) {
    return /\d/.test(myString);
}

function imprimir()
{
    var content = document.getElementsByClassName("concontent")[0];
    var tab = document.getElementsByTagName('h3')[0];
    var title = document.getElementById("title").value;
    var d = document.getElementsByClassName("d");
    var imprimir = document.getElementById("imprimir");
    var h = document.getElementById("h");

    content.style.width = "90%";
    content.style.border = "none";

    h.style.display = "none";

    imprimir.style.display = "none";

    tab.innerText = title;

    for(var i = 0; i < d.length; i++)
    {
        d[i].style.display = "none";
    }

    print();

    content.style.width = null;
    content.style.border = null;

    h.style.display = null;

    imprimir.style.display = null;

    tab.innerText = "Consultar " + title;

    for(var i = 0; i < d.length; i++)
    {
        d[i].style.display = null;
    }
}