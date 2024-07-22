mysql -u root -p ・データーベース一覧表示 mysql> SHOW DATABASES;

・データーベース作成 mysql> CREATE DATABASE SpringMybatis;

--テーブル内を確認（この時点で作成されていない） SELECT * FROM springmybatis; --playersがあれば削除(ないのでテーブルを作成） DROP TABLE IF EXISTS symphogear_players;

・テーブル作成 mysql> CREATE TABLE IF NOT EXISTS symphogear_players( -> id VARCHAR(20) NOT NULL, -> name VARCHAR(20) NOT NULL, -> symphogear_name VARCHAR(20) NOT NULL, -> PRIMARY KEY(id) -> ); Query OK, 0 rows affected (0.04 sec)

・テーブル内容確認 mysql> SELECT * FROM symphogear_players;

UserMapper.javaの //★symphogear_playersがテーブルなのでそれを作成する必要がある
@Insert("INSERT INTO symphogear_players(id,name,symphogear_name)" + "VALUES (#{id},#{name},#{symphogear_name})") void create(UserSearchRequest userAdd);



```controller.java

 ①~~splayDelete(@PathVariable("id") String id) {
        // UserSearchRequest オブジェクトを作成
        UserSearchRequest deleteRequest = new UserSearchRequest();
        deleteRequest.setId(id); // ID を設定~~
```
405HTTPメソッドの不一致: コントローラーメソッドが DELETE リクエスト専用であるため、POST リクエストを受け付けない。
フォームメソッドの制約: ブラウザが DELETE メソッドをサポートしていないため、実際には POST メソッドでリクエストが送信される。
この問題を解決するには、解決したコードのように、@RequestMappingを使って複数のHTTPメソッド（POST と DELETE）をサポートした。となります。

Q:サイトのコードのままで動く人と、動かない人がいる差が何なのかなについては引き出しが足らず分かりかねます…
↑上記は本来の解決じゃない方法である、Thymeleafを使用しているので、「th:method = "delete"」で問題ないはず！
"th:method delete" 405　「application.properties」に追加必要でした。

②★application.properties において spring.mvc.hiddenmethod.filter.enabled: true は、Spring MVC アプリケーションで隠しメソッドフィルターを有効にする設定です。
この設定を有効にすると、HTML フォームから送信される隠しフィールド _method を使用して、HTTP メソッドをオーバーライドすることができます。
例えば、フォームが本来 POST メソッドしかサポートしていない場合でも、このフィルターを使用することで PUT や DELETE といった他の HTTP メソッドをエミュレートできます。

```controller.java

    @RequestMapping(value = "/user/delete/id={id}", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String displayDelete(@PathVariable("id") String id) {
        // UserSearchRequest オブジェクトを作成
        UserSearchRequest deleteRequest = new UserSearchRequest();
        deleteRequest.setId(id); // ID を設定
```
