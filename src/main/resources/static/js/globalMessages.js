function createGlobalErrorMessage(message) {
    createGlobalMessage(
        message,
        "danger",
        "glyphicon glyphicon-remove-sign"
    );
}
function createGlobalInfoMessage(message) {
    createGlobalMessage(
        message,
        "info",
        "glyphicon glyphicon-info-sign"
    );
}
function createGlobalSuccessMessage(message) {
    createGlobalMessage(
        message,
        "success",
        "glyphicon glyphicon-info-sign"
    );
}
function createGlobalMessage(message, type, icon) {
    $(
        '<div class="alert alert-' +
        type +
        ' alert-dismissible"><span class="' +
        icon +
        '"></span> <span>' +
        message +
        '</span><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>'
    ).appendTo("#global-messages");
}