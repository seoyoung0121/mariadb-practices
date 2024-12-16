package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookmall.vo.BookVo;

public class BookDao {

	public void insert(BookVo vo) {
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("insert into book values(null, ?, ?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
			){
			
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setInt(2, vo.getPrice());
			pstmt1.setLong(3, vo.getCategoryNo());
			
			int count = pstmt1.executeUpdate();
			boolean result = count == 1;
			if (result) {
				ResultSet rs = pstmt2.executeQuery();

				if (rs.next()) {
					vo.setNo(rs.getLong(1));
				}
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}

	public void deleteByNo(Long no) {
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from book where no = ?");
		){
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		
	}
	
	
}
