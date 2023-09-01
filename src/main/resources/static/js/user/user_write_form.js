function writeBoard(editor) {
    console.log('writePost() CALLED!!');
    console.log("write : " + editor.getData());

    const form = document.board_write_form;

    if (form.b_title.value == '') {
        alert('INPUT TITLE');
        form.b_title.focus();

    } else if (editor.getData() == undefined) {
        alert('INPUT BODY');
        form.b_content.focus();

    } else if (editor.getData().trim() === '') {
        alert('INPUT BODY');
        editor.editing.view.focus(); // 에디터에 포커스를 줄 때는 에디터의 내부 요소로 이동

    }  else {
        let inputFile = $("#section_wrap .write_content input[name='file']");
        let files = inputFile[0].files;
        console.log(files)
        ajax_writePost(form.b_content.textContent, form.b_title.value, editor, files[0]);

    }

}

function ajax_writePost(b_content, b_title, editor, file) {

    // const content = {
    //     'b_title': b_title,
    //     // 'b_body': editor.getData().replace(/(<([^>]+)>)/ig,"")
    //     'b_content' :editor.getData(),
    //     'b_thumbnail' : b_thumbnail
    // };

    let formData = new FormData();
    formData.append("b_content", editor.getData());
    formData.append("b_title", b_title);
    // formData.append("file", file);

    if (file != undefined) {
        console.log('FILE IS NOT UNDEFINED!!');
        formData.append("file", file);

    } else {
        console.log('FILE IS UNDEFIED!!');

    }

    $.ajax({
        url: '/user/user_write_confirm',
        method: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function(data) {

            console.log('AJAX SUCCESS - ajax_writePost()');
            console.log(" data "+data)

            if (data >= 1) {
                alert('writePost success!!');
                 location.href="/user/user_myPage"
            }

        },
        error: function(data) {
            console.log('AJAX ERROR - ajax_writePost()');
            alert('writePost fail!!');

        }

    })

}