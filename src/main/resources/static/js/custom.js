
$(".form-group input").change(function() {
  if ($(this).val() != "") {
    $(this).addClass('filled');
  } else {
    $(this).removeClass('filled');
  }
})


$(function () {
	
    $(".field-wrapper .field-placeholder").on("click", function () {
        $(this).closest(".field-wrapper").find("input").focus();
    });
		
		$(".field-wrapper input").blur(function(){
			 var value = $.trim($(this).val());
             if (value) {
                 $(this).closest(".field-wrapper").addClass("hasValue");
             } else {
                 $(this).closest(".field-wrapper").removeClass("hasValue");
             }
		});
			
					
 
      
            
            $(".field-wrapper input").on("keyup", function () {
                var value = $.trim($(this).val());
                if (value) {
                    $(this).closest(".field-wrapper").addClass("hasValue");
                } else {
                    $(this).closest(".field-wrapper").removeClass("hasValue");
                }
            });
 
        });