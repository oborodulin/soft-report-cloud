/*
 * Выполняет проверку кол-ва отмеченных записей (строк таблицы) для немассовых операций.
 * (Для перехода по ссылке для немассовых операций должна быть отмечена только одна запись)
 */
function bulkActionChecked(obj, event, checkName) {
	var len = $('input[name="' + checkName + '"]:checked').length;
	if (len != 1) {
		alert('Отмечено более одной записи. Отметьте, пожалуйста, только одну запись...');
	} else {
		var href = $(obj).attr('href');
		var id = $('input[name="' + checkName + '"]:checked').map(function(){return $(this).val()}).get();
		var newHref = href.replace('[id]', id);
		//alert(newHref);
		window.location.href = newHref;
	}
	event.preventDefault();
	return false;
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