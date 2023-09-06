$(document).on('click','div.search_bar > svg', function (e){
    console.log("클릭!")
    let mainKeyword = $('input.main_search_bar').val();

    if(mainKeyword == ""){
        alert('검색어를 입력해주세요');
        $('input.main_search_bar').focus();
    }else{
        location.href = "/search?keyword="+mainKeyword;
    }
})