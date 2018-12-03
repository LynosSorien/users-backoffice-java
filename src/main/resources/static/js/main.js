$(document).ready(function() {
    multipleMagnifier();
    $("#fill-users-link").on("click", function() {
        createGlobalInfoMessage("Looking for new users and persisting...");
        $.ajax({
            url: "/api/users/fill",
            method: "POST",
            contentType: "application/json",
            success: function(data) {
                createGlobalSuccessMessage("The users has been created successfully");
            },
            error: function() {
                createGlobalErrorMessage("Unexpected error occurred while trying to create users");
            }
        });
    });
    userEditLoad();
});

function userEditLoad() {
    $(".user-edit-button").on("click", function() {
        var requestBody = new Object();
        $(".entity-value").each(function() {
            requestBody[$(this).attr("id")] = $(this).val();
        });
        $.ajax({
            url: "/api/user/edit",
            method: "POST",
            data: JSON.stringify(requestBody),
            contentType: "application/json",
            success: function() {
                createGlobalSuccessMessage("The user information has been changed successfully");
            },
            error: function() {
                createGlobalErrorMessage("Unexpected error occurred while trying change user information");
            }
        });
    });
}

function multipleMagnifier() {
    $(".multiple-magnifier").each(function () {
        var currentMagnifier = $(this);
        var magnifierType = currentMagnifier.attr("magnifier-type");
        $(this)
            .find(".magnifier-key")
            .each(function () {
                $(this).change(function () {
                    var values = [];
                    currentMagnifier.find(".magnifier-key").each(function () {
                        values.push(
                            {
                                "key": $(this).attr("magnifier-key"),
                                "value": $(this).val()
                            }
                        );
                    });
                    var allNeededValue = currentMagnifier.attr("all-needed");
                    var allNeeded = allNeededValue != null &&
                        "" !== allNeededValue &&
                        allNeededValue === "true";
                    var areAllNotNull = values.every(function (i) {
                        return i.value !== null && i.value !== "";
                    });
                    if (areAllNotNull || !allNeeded) {
                        var action = currentMagnifier.attr("action") + "?";
                        var i;
                        for (i = 0; i < values.length; i++) {
                            action += values[i].key + "=" + values[i].value + "&";
                        }
                        action += "end";
                        $.ajax({
                            url: action,
                            method: "GET",
                            contentType: "application/json",
                            success: function (data) {
                                data = data.payload;
                                currentMagnifier
                                    .find(".magnifier-target")
                                    .each(function () {
                                        if (magnifierType === "simple") {
                                            $(this).val(data[$(this).attr("magnifier-target")]);
                                        }
                                        if (magnifierType === "list") {
                                            var tbodyTable = $($(this).find("tbody"));
                                            tbodyTable.empty();
                                            var theadTable = $(this).find("thead").find("tr").find("th");
                                            var index, j, jObject, currentValue, insertValue="";
                                            for (index = 0; index < data.length; index++) {
                                                currentValue = data[index];
                                                if (currentValue) {
                                                    insertValue += "<tr magnifier-table-index='"+index+"'>";
                                                    for (j = 0; j < theadTable.length; j++) {
                                                        jObject = $(theadTable[j]);
                                                        insertValue += "<td>";
                                                        if (jObject.attr("magnifier-deleter")) {
                                                            insertValue+='<button class="btn btn-danger magnifier-delete-button" magnifier-index="'+index+'" magnifier-deleter="'+currentValue[jObject.attr("magnifier-deleter")]+'">Delete</button>';
                                                        } else if (jObject.attr("magnifier-link")) {
                                                            if (jObject.attr("magnifier-type") === "info") {
                                                                insertValue+='<a class="btn btn-info" href="'+jObject.attr("magnifier-link")+"?"+jObject.attr("magnifier-key")+"="+currentValue[jObject.attr("magnifier-key")]+'">View</a>';
                                                            } else {
                                                                insertValue+='<a class="btn btn-warning" href="'+jObject.attr("magnifier-link")+"?"+jObject.attr("magnifier-key")+"="+currentValue[jObject.attr("magnifier-key")]+'">Edit</a>';
                                                            }
                                                        } else {
                                                            insertValue += currentValue[jObject.attr("magnifier-target")];
                                                        }
                                                        insertValue += "</td>";
                                                    }
                                                    insertValue += "</tr>";
                                                }
                                            }
                                            tbodyTable.append(insertValue);
                                            $(".magnifier-delete-button").each(function() {
                                                var buttonObject = $(this);
                                                $(this).on("click", function() {
                                                    $.ajax({
                                                        url: "/api/users/by?username="+$(this).attr("magnifier-deleter"),
                                                        method: "DELETE",
                                                        contentType: "application/json",
                                                        success: function(data) {
                                                            $("[magnifier-table-index="+buttonObject.attr("magnifier-index")+"]").remove();
                                                        }
                                                    });
                                                });
                                            });
                                        }
                                    });
                            }
                        });
                    }
                });
            });
    });
}