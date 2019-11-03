$(document).ready(function() {
	console.log('ready to serve...')
})

function showMore() {
	$.ajax({
		url : $("#f1").attr("action"),
		type : "POST",
		data : $("#f1").serialize(),
		success : function(data) {
			if (!$.trim(data)) {
				alert("No more data found !");
				$("#btshow").attr('disabled', 'disabled');
			} else {
				$("#newcontent").empty();
				$("#newcontent").append(data)
			}
		},
		error : function(e) {
			console.log('some thing error')
		}
	});
}