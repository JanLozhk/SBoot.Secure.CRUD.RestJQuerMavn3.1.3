jQuery(document).ready(function() {
    $('#new-user-form-btn-save').on('click', function(event) { createUser(); });
    $('#modal-edit-btn-save').on('click', function(event) { updateUser(); });
    $('#modal-delete-btn-save').on('click', function(event) { deleteUser(); });
    $('#spanAdminAll').on('click', function(event) { updateUsers(); });
    $('#spanCurrUser').on('click', function(event) { drawCurrentUser(); });
    if ($('#spanAdminAll')[0]) {
        updateUsers();
    } else {
        drawCurrentUser();
    }
});

/*
function getData() {
    console.log('123');

    let response = await fetch('http://localhost:8080/admin/api/all');
    if (response.ok) {
        let json = await response.json();
        console.log(json);
    }
}

function getUserById(id) {
    console.log(id);

    let response = await fetch('http://localhost:8080/admin/' + id);
    if (response.ok) {
        let json = await response.json();
        console.log(json);
    }
}
function openExistingUserTab() {

    let form = document.getElementsByClassName("edit-form")[0];
    form.removeEventListener("submit", submitDelete, false);
    form.addEventListener("submit", submitEdit, false);
}

function submitDelete(e){
    e.preventDefault();
    let form = document.getElementsByClassName("edit-form")[0];
    await fetch("api/users/", {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json; charset=utf-8',
        },
        body: JSON.stringify(Object.fromEntries(getFormData(form)))
    });
    closeModal();
    await redrawTable();
    return false;
}*/

function openEditTab(id) {
    $('#edit-user-form').trigger('reset');
    $('#modal-edit').modal();
    getUser(id, function(user) {
        $('#edit-user-form [name=id]').val(user.id);
        $('#edit-user-form [name=firstName]').val(user.firstName);
        $('#edit-user-form [name=lastName]').val(user.lastName);
        $('#edit-user-form [name=age]').val(user.age);
        $('#edit-user-form [name=email]').val(user.email);
        $('#edit-user-form [name=login]').val(user.login);
        $('#edit-user-form [name=password]').val(user.password);
        $('#edit-user-form [name=authorityList]').val(user.authorityList?.map(function(a) { return a.authority; }));
    });
}

function openDeleteTab(id) {
    $('#delete-user-form').trigger('reset');
    $('#modal-delete').modal();
    getUser(id, function(user) {
        $('#delete-user-form [name=id]').val(user.id);
        $('#delete-user-form [name=firstName]').val(user.firstName);
        $('#delete-user-form [name=lastName]').val(user.lastName);
        $('#delete-user-form [name=age]').val(user.age);
        $('#delete-user-form [name=email]').val(user.email);
        $('#delete-user-form [name=login]').val(user.login);
        $('#delete-user-form [name=password]').val(user.password);
        $('#delete-user-form [name=authorityList]').val(user.authorityList?.map(function(a) { return a.authority; }));
    });
    // $('#modal-delete-btn-save').on('click', function(event) { deleteUser($('#delete-user-form [name=id]').val(data.id)); });

}

function drawCurrentUser() {
    $.ajax({
        url: '/admin/api/',
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        success: function(user) { redrawTable([user], false) },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}

function getUser(id, func) {
    $.ajax({
        url: '/admin/api/' + id,
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) { func(data); },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}

// function

function createUser() {
    var userData = $('#new-user-form').jsonify();
    if (!validateNewUser(userData)) {
        return;
    }
    if (typeof userData.authorityList === 'string') {
        userData.authorityList = [userData.authorityList];
    }
    $.ajax({
        url: '/admin/api/',
        type: 'post',
        data: JSON.stringify(userData),
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            redrawTable(data, true);
            $('#list-tab').click();
            $('#new-user-form').trigger('reset');
        },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}

function updateUser() {
    var userData = $('#edit-user-form').jsonify();
    if (!validateNewUser(userData)) {
        return;
    }
    if (typeof userData.authorityList === 'string') {
        userData.authorityList = [userData.authorityList];
    }
    $.ajax({
        url: '/admin/api/' + userData.id,
        type: 'put',
        data: JSON.stringify(userData),
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            redrawTable(data, true);
            $('#modal-edit').modal('toggle');
        },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}

function deleteUser() {
    var userData = $('#delete-user-form').jsonify();
    $.ajax({
        url: '/admin/api/' + userData.id,
        type: 'delete',
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            redrawTable(data, true); //необязательно тчк
            $('#modal-delete').modal('toggle');
        },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}

function updateUsers() {
    $.ajax({
        url: '/admin/api/all',
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) { redrawTable(data, true); },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}

function redrawTable(userList, showButtons) {
    var $table = $('.table-users tbody');
    $table.empty();
    userList.forEach((user, index) => {
        var authorities = user.authorityList.map(function(a) { return a.authority; }).join('<br/>');
        var btnEdit = '<a class="btn btn-primary btn-edit" data-id="' + user.id + '">Edit</a>';
        var btnDelete = '<a class="btn btn-danger btn-delete" data-id="' + user.id + '">Delete</a>';
        var buttons = btnEdit + ' ' + btnDelete;

        $table.append('<tr>' +
            '<td>' + user.id + '</td>' +
            '<td>' + user.firstName + '</td>' +
            '<td>' + user.lastName + '</td>' +
            '<td>' + user.login + '</td>' +
            '<td>' + user.age + '</td>' +
            '<td>' + user.email + '</td>' +
            '<td>' + authorities + '</td>' +
            '<td>' + (showButtons ? buttons : '') + '</td>' +
            '</tr>');
    });
    $('.btn-delete').on('click', function(event) { openDeleteTab( event.currentTarget.getAttribute('data-id')); });
    $('.btn-edit').on('click',   function(event) { openEditTab(event.currentTarget.getAttribute('data-id')); });
}

/*function redrawTableForOneUser(user) {
    var $table = $('.table-users tbody');
    $table.empty();
    var authorities = user.authorityList.map(function(a) { return a.authority; }).join('<br/>');
    /!*var btnEdit = '<a class="btn btn-primary btn-edit" data-id="' + user.id + '">Edit</a>';
    var btnDelete = '<a class="btn btn-danger btn-delete" data-id="' + user.id + '">Delete</a>';
    var buttons = btnEdit + ' ' + btnDelete;*!/
    $table.append('<tr>' +
        '<td>' + user.id + '</td>' +
        '<td>' + user.firstName + '</td>' +
        '<td>' + user.lastName + '</td>' +
        '<td>' + user.login + '</td>' +
        '<td>' + user.age + '</td>' +
        '<td>' + user.email + '</td>' +
        '<td>' + authorities + '</td>' +
        '<td></td>' +
        '</tr>');*/
    /*$('.btn-delete').on('click', function(event) { deleteUser( event.currentTarget.getAttribute('data-id')); });
    $('.btn-edit').on('click',   function(event) { openEditTab(event.currentTarget.getAttribute('data-id')); });*/
// }

function validateNewUser(user) {
    if (!user.firstName) {
        showError('Не заполнено поле firstName');
        return false;
    }
    if (!user.lastName) {
        showError('Не заполнено поле lastName');
        return false;
    }
    if (!user.age) {
        showError('Не заполнено поле age');
        return false;
    }
    if (!user.email) {
        showError('Не заполнено поле email');
        return false;
    }
    if (!user.login) {
        showError('Не заполнено поле login');
        return false;
    }
    if (!user.password) {
        showError('Не заполнено поле password');
        return false;
    }
    if (!user.authorityList) {
        showError('Не заполнено поле Role');
        return false;
    }
    return true;
}

function showError(text) {
    $('#modal-error-text').text(text);
    $('#modal-error').modal();
}