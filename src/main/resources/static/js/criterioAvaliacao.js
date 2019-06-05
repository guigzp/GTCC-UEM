$(function() {

	$(".field-quantidade-avaliacoes").on("keyup", function() {
		
		let valor = $(this).val();
		if(valor > 0)
		{
			let html = "";
			for(let i = 1; i <= valor; i++)
			{
				html += '<div class="form-group row justify-content-center" style="padding-left: 20px">';
				html += '<div class="input-group col-xl-4">';
				html += '<input type="number" required="true" name="qtd_notas_'+i+'" id="qtd_notas_'+i+'" class="field-peso-nota form-control"';
				html += 'placeholder="Qtd. Notas da '+i+'° Avaliação" aria-describedby="basic-addon1">';
				html += '</div>';
				html += '<div class="input-group col-xl-6"></div>';
				html += '</div>';
				
				html += '<div class="qtd_notas_'+i+'_pesos">';
				html += '</div>';
			}
			
			//escreve o html na div-notas
			$(".div-notas").html(html);
			
			$(".field-peso-nota").on("keyup", function() {
				let target = $(this).attr('name');
				let valor = $(this).val();
				
				console.log("valor: " + valor + ", nome: " + target);
				if(valor > 0)
				{
					let html = "";
					for(let i = 1; i <= valor; i++)
					{
						html += '<div class="form-group row justify-content-center" style="padding-left: 40px">';
						html += '<div class="input-group col-xl-4">';
						html += '<input type="number" required="true" name="'+target+'_pesos_'+i+'" id="'+target+'_pesos_'+i+'" class="form-control"';
						html += 'placeholder="Peso da '+i+'° Nota" aria-describedby="basic-addon1">';
						html += '</div>';
						html += '<div class="input-group col-xl-6"></div>';
						html += '</div>';
					}
					
					//escreve o html nos pesos das notas
					$("."+target+"_pesos").html(html);
				}
				else if(valor === null || valor <= 0)
				{
					$("."+target+"_pesos").html("");
				}
				
				
			});
		}
		else if(valor === null || valor <= 0)
		{
			$(".div-notas").html("");
		}
		
	});

});

