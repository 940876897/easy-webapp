var euFn={
    codeEdit:{
        showEditor:function(editorId,mode,theme){
            if(!editorId) return false;

		    // 检查全局变量中是否存在代码编辑器对象,如果没有,就创建一个.
            if(!euFnTmpVar.codeEditor) euFnTmpVar.codeEditor={} ;
			// 检查全局变量中是否有这个代码编辑器,如果有,就直接返回.
			if(euFnTmpVar.codeEditor[editorId]) return;
            
            var editorHtml = '<div style="height: 300px;width: 600px;clear: both;" class="resize editorPre_' + editorId + '"><pre id="editorPre_' + editorId + '" style="margin: 0;height: 300px;width: 600px;"  class="resize editorPre_' + editorId + '">'+$("#"+editorId).html()+'</pre></div>';
            //  暂时先使用代码编辑器的change事件同步内容到原textarea中.
            editorHtml +='<div class="editorPre_' + editorId + ' "><a href="javascript:euFn.codeEdit.returnValue(\''+ editorId + '\');">确定</a> &nbsp;&nbsp;';
			editorHtml +='<a href="javascript:euFn.codeEdit.heightAdd(\''+ editorId + '\',100);">加高</a> &nbsp;&nbsp;';
			editorHtml +='<a href="javascript:euFn.codeEdit.heightAdd(\''+ editorId + '\',-100);">减高</a> &nbsp;&nbsp;';
			editorHtml +='<a href="javascript:euFn.codeEdit.widthAdd(\''+ editorId + '\',100);">加宽</a> &nbsp;&nbsp;';
			editorHtml +='<a href="javascript:euFn.codeEdit.widthAdd(\''+ editorId + '\',-100);">减宽</a> &nbsp;&nbsp;';
            
			editorHtml +='<a href="http://ace.ajax.org" target="_blank" rel="ace_ajax_org_help">?</a> &nbsp;&nbsp;';
			
			editorHtml +='code类型: <select id="ace_mode'+editorId+'" style="float: none;" onchange="euFn.codeEdit.setMode(\''+ editorId + '\',this);"><option value="html">HTML</option><option value="json">JSON</option><option value="javascript">JavaScript</option><option value="css">CSS</option><option value="xml">XML</option><option value="text">Text</option><option value="jsp">JSP</option><option value="java">Java</option><option value="sql">SQL</option></select>&nbsp;&nbsp;';
			
			editorHtml +='样式: <select id="theme'+editorId+'" style="float: none;width: 100px;" onchange="euFn.codeEdit.setTheme(\''+ editorId + '\',this);"><optgroup label="Bright"><option value="chrome">Chrome</option><option value="clouds">Clouds</option><option value="crimson_editor">Crimson Editor</option><option value="dawn">Dawn</option><option value="dreamweaver">Dreamweaver</option><option value="eclipse">Eclipse</option><option value="github">GitHub</option><option value="solarized_light">Solarized Light</option><option value="textmate">TextMate</option><option value="tomorrow">Tomorrow</option><option value="xcode">XCode</option></optgroup><optgroup label="Dark"><option value="ambiance">Ambiance</option><option value="chaos">Chaos</option><option value="clouds_midnight">Clouds Midnight</option><option value="cobalt">Cobalt</option><option value="idle_fingers">idleFingers</option><option value="kr_theme">krTheme</option><option value="merbivore">Merbivore</option><option value="merbivore_soft">Merbivore Soft</option><option value="mono_industrial">Mono Industrial</option><option value="monokai">Monokai</option><option value="pastel_on_dark">Pastel on dark</option><option value="solarized_dark">Solarized Dark</option><option value="twilight">Twilight</option><option value="tomorrow_night">Tomorrow Night</option><option value="tomorrow_night_blue">Tomorrow Night Blue</option><option value="tomorrow_night_bright">Tomorrow Night Bright</option><option value="tomorrow_night_eighties">Tomorrow Night 80s</option><option value="vibrant_ink">Vibrant Ink</option></optgroup></select>&nbsp;&nbsp;';
			editorHtml +='</div>';

            $("#"+editorId).after(editorHtml);
            $("#"+editorId).hide();
            var _editor = ace.edit('editorPre_'+editorId);
            if(!mode)
                mode = "html";
            $("#ace_mode"+editorId).val(mode);
            _editor.getSession().setMode("ace/mode/"+mode);
            if(!theme)
                theme="chrome";
            $("#theme"+editorId).val(theme);
            _editor.setTheme("ace/theme/"+theme);
            _editor.getSession().setUseWrapMode(true);
			// 记录这个代码编辑器到全局变量中.
            euFnTmpVar.codeEditor[editorId] = _editor;

			//在修改了内容时,同步保存到原输入框内:
			_editor.getSession().on('change', function(e) { 
				$("#"+editorId).text(euFnTmpVar.codeEditor[editorId].getValue());
			});

        },
		setTheme:function(editorId,selectEl){ 
            euFnTmpVar.codeEditor[editorId].setTheme("ace/theme/"+$(selectEl).val());
        },
        setMode :function(editorId,selectEl){ 
            euFnTmpVar.codeEditor[editorId].getSession().setMode("ace/mode/"+$(selectEl).val());
        },
        returnValue :function(editorId){ 
			$("#"+editorId).text(euFnTmpVar.codeEditor[editorId].getValue());
			$("#"+editorId).show();
			$(".editorPre_" + editorId ).remove();
            euFnTmpVar.codeEditor[editorId] = null;
        },
        widthAdd :function(editorId, addWidth){ 
		    var newWidth = $("#editorPre_"+editorId).width()+addWidth;
			$(".resize.editorPre_"+editorId).width(newWidth); 
			euFnTmpVar.codeEditor[editorId].resize();
        },
        heightAdd :function(editorId, addHeight){ 
		    var newWidth = $("#editorPre_"+editorId).height()+addHeight;
			$(".resize.editorPre_"+editorId).height(newWidth); 
			euFnTmpVar.codeEditor[editorId].resize();
        }

    }

}

// euFn 方法中产生的临时变量,请注意及时删除无用或过期的变量.
var euFnTmpVar={

}
 
