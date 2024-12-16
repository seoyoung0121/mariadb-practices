package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookshop.vo.BookVo;

public class BookDao {
	public int insert(BookVo vo) {
		int count = 0;

		try (Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("insert into book(name, author_no) values(?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
			){ // close 있는 애들만 저래 씀, 그럼 알아서 닫아줌 
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setLong(2, vo.getAuthorId());
			count = pstmt1.executeUpdate();
			
			ResultSet rs = pstmt2.executeQuery();
			vo.setId(rs.next()? rs.getLong(1) : null);
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;
	}
	
	public int deleteAll() {
		int count = 0;

		try (Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from book");
			){ // close 있는 애들만 저래 씀, 그럼 알아서 닫아줌 
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;
		
	}
	public int update(Long id, String status) {
		int count = 0;

		try (Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update book set status = ? where no = ?");
			){ // close 있는 애들만 저래 씀, 그럼 알아서 닫아줌 
			pstmt.setString(1, status);
			pstmt.setLong(2, id);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;
		
	}

	public List<BookVo> findAll() {
		List<BookVo> list = new ArrayList<>();
		try(
				Connection conn=getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select a.no, a.name, b.name, a.status from book a join author b on a.author_no = b.no;");
		){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String authorName = rs.getString(3);
				String status = rs.getString(4);
				
				BookVo vo = new BookVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setAuthorName(authorName);
				vo.setStatus(status);
				list.add(vo);
			}
			rs.close();
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		return list;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.18:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return conn;
	}
	
}
