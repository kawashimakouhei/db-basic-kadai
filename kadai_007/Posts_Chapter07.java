package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement statement = null;

        // ユーザーリスト
        String[][] userList = {
            { "2023-02-08", "昨日の夜は徹夜でした・・" },
            { "2023-02-08", "お疲れ様です！" },
            { "2023-02-09", "今日も頑張ります！" },
            { "2023-02-09", "無理は禁物ですよ！" },
            { "2023-02-10", "明日から連休ですね！" }
        };
        
        int[][] userList2 = {
                { 1003, 13},
                { 1002, 12 },
                { 1003, 18 },
                { 1001, 17},
                { 1002, 20}
            };

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "kk19890722"
            );

            System.out.println("データベース接続成功");

            // SQLクエリを準備
            String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
            statement = con.prepareStatement(sql);

            // リストの1行目から順番に読み込む
            int rowCnt = 0;
            for( int i = 0; i < userList.length; i++ ) {
                // SQLクエリの「?」部分をリストのデータに置き換え
                statement.setInt(1, userList2[i][0]); // id
                statement.setString(2, userList[i][0]); // 日時
                statement.setString(3, userList[i][1]); // 内容
                statement.setInt(4, userList2[i][1]); // いいね数

                // SQLクエリを実行（DBMSに送信）
                System.out.println("レコード追加:" + statement.toString() );
                rowCnt = statement.executeUpdate();
                System.out.println( rowCnt + "件のレコードが追加されました");
            }
            
            
        } catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
        }
        
        con = null;
        Statement statement1 = null;

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "kk19890722"
            );

            System.out.println("データベース接続成功");

            // SQLクエリを準備
            statement1 = con.createStatement();
            String sql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";

            //　SQLクエリを実行（DBMSに送信）
            ResultSet result = statement1.executeQuery(sql);

            // SQLクエリの実行結果を抽出
            while(result.next()) {
                String posted_at = result.getDate("posted_at").toString();
                String post_content = result.getString("post_content");
                int likes = result.getInt("likes");
                System.out.println(result.getRow() + "件目：投稿日時=" + posted_at + "／投稿内容=" + post_content + "／いいね数=" + likes );
            }
        } catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if( statement1 != null ) {
                try { statement1.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
        }
    }
}
