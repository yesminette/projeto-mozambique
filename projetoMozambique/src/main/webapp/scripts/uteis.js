function release_chave(){
    var chave = document.getElementById("chave").value;
        if(chave == "palavras-chave" || chave == "Pesquisa por palavras-chave" || chave == "Busca"){
            document.getElementById("chave").value = "";
        }
}
function release_usuario(){
    var chave = document.getElementById("username").value;
        if(chave == "Usuário"){
            document.getElementById("username").value = "";
        }
}
function release_senha(){
    var chave = document.getElementById("password").value;
        if(chave == "Senha"){
            document.getElementById("password").value = "";
        }
}
function reduzirParaCaber(titulo,tamanho){
    if(titulo.length > tamanho){
        titulo = titulo.substr(0,tamanho-3)+"...";
    }
    return titulo;
}
function isVazio(valor){
    if(valor == null || valor == undefined){
        return true;
    }else{
        if(valor == ""){
            return true;
        }else{
            return false;
        }
    }
}


