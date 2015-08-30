<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<script>
		$(document).ready(function() {
			
			$("#tagsinput").tagsInput(
					{onAddTag:
							function(tag){
								var exist = false;
								var tagNames = getTagList(document.location.href);
								$.each(tagNames.split(","), function(index, value) {
									if (value == tag)
										exist = true;
								});
								if (!exist){
									movePage(tagNames+ ","+ tag+" ","");
								}
							},
					onRemoveTag:
						function(tag){
							var afterTags="";
							var tagNames = getTagList(document.location.href);
							$.each(tagNames.split(","), function(index, value) {
								if (value != tag)
									afterTags+=(","+value);
							});
							movePage(afterTags,"");
						}
					}
			);
			
			$.each(getTagList(document.location.href).split(","), function(index, value) { 
				  $('#tagsinput').addTag(value);
			});
			
			$('.tag-name').click(
					function() {
						var tagname = $(this).text();
						var exist = false;
						var tagNames = getTagList(document.location.href);
						
						if (tagNames.length == 0 || tagNames == "")
							movePage(tagname,"");
						
						$.each(tagNames.split(","), function(index, value) {
							if (value == tagname)
								exist = true;
						});
						if (!exist){
							movePage(tagNames+ ","+ tagname+" ","");
						}
					});
			
			$("input").not( "#tagsinput_tag" ).focus(function() {
				$(".ui-footer").hide();
			});
			
			$("input").not( "#tagsinput_tag" ).focusout(function() {
				$(".ui-footer").show();
			});
			
		});
</script>
		<div data-theme="a" data-role="footer" data-position="fixed">
           <input data-mini="true" data-theme="c"  id="tagsinput" placeholder="태그를 입력해주세요!">	
        </div>