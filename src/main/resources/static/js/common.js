    // パスワード表示切替
    function myFunction(){
        let x =document.getElementById("password");
        let y =document.getElementById("hide1");
        let z =document.getElementById("hide2");

        if(x.type === 'password'){
            x.type ="text";
            y.style.display ="block";
            z.style.display ="none";
        }
        else{
            x.type ="password";
            y.style.display ="none";
            z.style.display ="block";
        }
    }
    // クリアボタン押下時
    function clearText() {
	    // input属性の値をクリア
        $('#clearForm').find('input').each(function(index, element ){
            element.value = ''
        });
        // selectの値をクリア
        $('#clearForm').find('select').each(function(index, element ){
            element.value = ''
        });
    }

    /* ヘッダーリンク */
    // アカウント管理リンク押下時
    function toAccountList() {
        let form = document.createElement('form');
        form.action = '/account_list';
        form.method = 'get';
        form.submit();
    }
    // 管理画面TOP押下時
    function toTop() {
        let form = document.createElement('form');
        form.action = '/top';
        form.method = 'get';
        form.submit();
    }
    
        // 打ち合わせ予定管理押下時
    function toMtgList() {
        let form = document.createElement('form');
        form.action = '/mtg_list';
        form.method = 'get';
        form.submit();
    }
    
    // ログアウト押下時
    function logout() {
        let form = document.createElement('form');
        form.action = '/logout';
        form.method = 'get';
        form.submit();
    }

    // バーガーメニュー
    $(function() {
        $('.hamburger').click(function() {
            $(this).toggleClass('active');
 
            if ($(this).hasClass('active')) {
                $('.globalMenuSp').addClass('active');
            } else {
                $('.globalMenuSp').removeClass('active');
            }
        });
    });
    // ブラウザバック無効(※どのページで無効にするか考慮する)
 //   $(function() {
  //      history.pushState(null, null, null);

   //     $(window).on("popstate", function(){
   //         history.pushState(null, null, null);
   //     });
   // }); 