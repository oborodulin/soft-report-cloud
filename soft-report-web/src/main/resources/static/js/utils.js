/*
 * Выполняет проверку кол-ва отмеченных записей (строк таблицы) для немассовых операций
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

/*
 * Устанавливает признак продолжения работы с формой после отправки данных на
 * сервер
 */
function setFormContinueMark(formId) {
	var action = $("#" + formId).attr("action");
	var newAction = action.replace('/false', '/true');
	//alert(newAction);
	$("#" + formId).attr("action", newAction);
	$("#" + formId).submit();
}