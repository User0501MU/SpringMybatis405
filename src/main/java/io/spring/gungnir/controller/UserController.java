package io.spring.gungnir.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.spring.gungnir.dto.UserSearchRequest;
import io.spring.gungnir.entity.User;
import io.spring.gungnir.service.UserService;

@Controller//コントローラークラスの宣言
public class UserController {

    @Autowired//サービスクラスのnewを自動でしてくれるアノテーション
    UserService userService;

    /*
     * URL（localhost:8080/user）でアクセスでトップ画面の表示
     */
    @GetMapping(value = "/user")//このURLにマッピングされておりGetするとメソッドが動く
    public String display() {
        return"top";//top.htmlの画面を表示
    }


    /*
     * 一件検索するためのフォームを表示
     */
    @GetMapping(value = "/user/search")//トップ画面でto search formを押下した時の画面遷移
    public String displaySearch() {
            return"player_search";//player_search.htmlの画面を表示
    }


    /*
     * 一件検索の実行
     */
    @RequestMapping(value = "/user/id_search", method = RequestMethod.POST )
//searchボタンを押され/user/id_searchが呼ばれたとき動く。フォームに入力されたIDをpostで受け取る

public String search(@ModelAttribute UserSearchRequest userSearchRequest, Model model){
//@ModelAttributeがpostされたリクエストをuserSearchReqesutに格納する（同時にnewする）

User user = userService.search(userSearchRequest);//サービスクラスのメソッドにuserSerchReqestを引数として渡す
        model.addAttribute("user_info",user);
//サービスクラスメソッドの結果をuserに詰める。テンプレートで使うキーとして”user_info”を指定

return"player_search";//player_search.htmlの画面を表示
    }


  /*
     * 全件表示
     */
    @PostMapping(value = "/user/list")//player listボタンで/user/listが呼ばれたとき動く
    public String getUserList(Model model) {
        List <User> list = userService.searchAll();
        //リスト型でサービスクラスのインスタンスメソッドを呼び出す（リスト取得の指示だし）
        model.addAttribute("users_info", list);//結果をlistに詰める。（users_infoがキー）
        return "list";  //list.htmlの画面を表示
    }


/*新規プレイヤー登録画面を表示
     * 画面遷移のみ
     */
    @PostMapping(value = "/user/add")//to sing up formのボタンで/user/addが呼ばれたとき動く
    public String displayAdd() {
        return "add_player";//add_playerの画面を表示
    }


/*登録の実行
     * 登録情報の表示
     */
    @RequestMapping(value = "user/add_comp", method = RequestMethod.POST )
//sign upが押され/user/add_compが呼ばれた時動く。フォームの内容をpostで受け取る

public String create(@ModelAttribute UserSearchRequest userAdd, Model model) {
        //postされたリクエストをusesrAddに格納+new

userService.create(userAdd);//追加処理の実行をサービスクラスに指示
        User user = userService.createCheck(userAdd);//追加した情報をセレクトしとってくるよう指示
        model.addAttribute("user_add", user);//セレクト結果をuserに詰める。（user_addをキー）
        return "add_comp";  //add_comp.htmlの画面を表示
    }


/*
     * 編集画面への遷移
     */
    @PostMapping(value = "/user/conf/id={id}")//updateが押されたとき動く
    public String editSelect(@PathVariable("id")String id ,Model model){
//@PathVariableでURL内のidを引数に取得

        User user = userService.editSelect(id);//取得したidを渡してセレクト指示
        model.addAttribute("user_select",user);//セレクト結果をuserに詰める。（user_selectがキー）
        return "conf_player";//conf_player.htmlを表示
 }

/*
　 *編集の実行
　 */
    @RequestMapping(value = "/user/edit/id={id}", method = RequestMethod.POST)//editボタンが押されたとき動く
    public String update(@ModelAttribute UserSearchRequest edit) {
        userService.update(edit);//postされたリクエストが格納されたeditを引数にupdateメソッド呼び出し

        return "edit";//edit.htmlを表示
    }

    /*
     * 削除の実行
     */
    @RequestMapping(value = "/user/delete/id={id}", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String displayDelete(@PathVariable("id") String id) {
        // UserSearchRequest オブジェクトを作成
        UserSearchRequest deleteRequest = new UserSearchRequest();
        deleteRequest.setId(id); // ID を設定

        userService.deleteOne(deleteRequest); // UserSearchRequest を渡して削除処理を実行
        return "delete"; // delete.html を表示
    }
}

/*削除実行にpostが宣言されていなかった件
 * @Controller

これで、UserController クラスが Spring MVC のコントローラーやでって宣言してるんや。
@Autowired

これで、UserService のインスタンスを自動で注入するように指定してるんや。わざわざ new せんでも、Spring が用意してくれるんや。

@RequestMapping(value = "/user/delete/id={id}", method = {RequestMethod.POST, RequestMethod.DELETE})
ここで、/user/delete/id={id} って URL パターンに対して POST または DELETE メソッドが呼ばれたときに、このメソッドが動くって指定してるんや。

public String displayDelete(@PathVariable("id") String id)
このメソッドは、id パラメータを URL から取り出して、displayDelete メソッドの id 引数として使うんや。@PathVariable で URL の {id} を引っこ抜いてるんや。

UserSearchRequest deleteRequest = new UserSearchRequest();
UserSearchRequest の新しいオブジェクトを作ってるんや。このオブジェクトを使って、削除処理のためのリクエストを準備するんや。

deleteRequest.setId(id);
さっき作った deleteRequest オブジェクトに、URL から取り出した id をセットしてるんや。

userService.deleteOne(deleteRequest);
userService の deleteOne メソッドを呼び出して、さっきの deleteRequest を渡して削除処理を実行してるんや。

return "delete";
処理が終わったら、delete.html ってテンプレートを返して、削除完了の画面を表示するんや。
これで、関西弁での説明は終わりやで！*/