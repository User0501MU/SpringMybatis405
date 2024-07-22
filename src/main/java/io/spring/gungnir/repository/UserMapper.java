//repository(mapperはSQL文をマッピグしたものを使いやすくしたもの。)
package io.spring.gungnir.repository;
//MyBatisを使うときに使うアノテーションで、SQL文とJavaメソッドをマッピングするためのインターフェースや。
//これを使うことで、SQLクエリをJavaのメソッドとして呼び出せるんや。
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import io.spring.gungnir.dto.UserSearchRequest;
import io.spring.gungnir.entity.User;

@Mapper //マッパークラスの宣言
public interface UserMapper {

    /*
     * プレイヤー情報検索
     */
    @Select("SELECT * FROM symphogear_players WHERE id = #{id}")
    User search(UserSearchRequest user);


    /*
     * 全件表示
     */
    @Select("SELECT * FROM symphogear_players")
    List<User> searchAll();


    /*
     * 新プレイヤー情報登録
     */
//★symphogear_playersがテーブル
    @Insert("INSERT INTO symphogear_players(id,name,symphogear_name)"
            + "VALUES (#{id},#{name},#{symphogear_name})")
    void create(UserSearchRequest userAdd);


    /*
     * 新追加情報表示
     */
    @Select("SELECT * FROM symphogear_players WHERE id = #{id}")
    User createCheck(UserSearchRequest userAdd);


    /*
     * 編集用の情報セレクト
     */
    @Select("SELECT * FROM symphogear_players WHERE id = #{id}")
    User editSelect(String id);


    /*
     * 情報編集
     */
    @Update("UPDATE symphogear_players "
            + "SET name = #{name}, symphogear_name = #{symphogear_name} "
            + "WHERE id = #{id}")
    void edit(UserSearchRequest edit);


    /*
     * レコード削除
     */
    @Delete("DELETE FROM symphogear_players WHERE id = #{id}")
    void deleteOne(UserSearchRequest delete);

}