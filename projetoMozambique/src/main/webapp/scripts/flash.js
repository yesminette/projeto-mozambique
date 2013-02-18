function flash(largura, altura, arquivo){
	var navegador = navigator.appName 
	if (navegador == "Microsoft Internet Explorer") {
		document.write('<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="'+largura+'" height="'+altura+'">');
		document.write('<param name="menu" value="false" />');
		document.write('<param name="movie" value="'+arquivo+'" />');
		document.write('<param name="quality" value="best" />');
		document.write('<param name="wmode" value="transparent" />');	
		document.write('<embed src="'+arquivo+'" wmode="transparent" quality="best" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="'+largura+'" height="'+altura+'"></embed>');
		document.write('</object>');	
	}else {
		document.write('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="'+largura+'" height="'+altura+'" id="base" align="middle">');
		document.write('<param name="allowScriptAccess" value="sameDomain" />');
		document.write('<param name="wmode" value="transparent" />');	
		document.write('<param name="movie" value="'+arquivo+'" /><param name="wmode" value="transparent" /><param name="quality" value="high" /><param name="bgcolor" value="#ffffff" /><embed src="'+arquivo+'" quality="high" bgcolor="#ffffff" width="'+largura+'" height="'+altura+'" name="base" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />');
		document.write('</object>');
	}
}