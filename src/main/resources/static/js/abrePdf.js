function abrePdf(){
	//print(conteudo)
   //var path0= janela.location.href;
   //console.log(path0);
   var bimestre= documento.querySelector('input[name="avaliacao"]:checked').value
   //console.log(bimestre)
   if(bimestre == "1"){
 	   setTimeout(function (){
     	   janela.open(path0 + "/show/1", "_blank", "");
     	   janela.open(path0 + "/show/2", "_blank", "");
     	   janela.open(path0 + "/show/3", "_blank", "");
 	   },100)
   }else{
	  setTimeout(function (){
     	   janela.open(path0 + "/show/1", "_blank", "");
 	   },100)
   }
}