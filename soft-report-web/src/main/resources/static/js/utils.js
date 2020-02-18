/*
 * Выполняет проверку кол-ва элементов для операций
 * 
 */
function bulkActionChecked(obj, event, checkName) {
	var len = $('input[name="' + checkName + '"]:checked').length;
	if (len != 1) {
		// alert(href+id);
		event.preventDefault();
		return false;
	} else {
		var href = $(obj).attr('href');
		var id = $('#' + checkName).val();
		window.location.href = href + id;
	}
}