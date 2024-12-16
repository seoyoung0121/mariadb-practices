package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;

public class CartDao {

	public void insert(CartVo vo) {
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into cart values(?, ?, ?, ?)");
			){
			pstmt.setLong(1, vo.getBookNo());
			pstmt.setLong(2, vo.getUserNo());
			pstmt.setInt(3, vo.getQuantity());
			pstmt.setInt(4, vo.getPrice());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}

	public List<CartVo> findByUserNo(Long no) {
		List<CartVo> result = new ArrayList<>();
		
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select a.book_no, a.quantity, a.price, b.title from cart a, book b where a.book_no = b.no and user_no = ?");
				){
				pstmt.setLong(1, no);
				
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Long book_no = rs.getLong(1);
					int quantity = rs.getInt(2);
					int price = rs.getInt(3);
					String title = rs.getString(4);

					CartVo vo = new CartVo();
					vo.setUserNo(no);
					vo.setBookNo(book_no);
					vo.setQuantity(quantity);
					vo.setPrice(price);
					vo.setBookTitle(title);
					result.add(vo);
				}
				rs.close();
				
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		return result;
	}

	public void deleteByUserNoAndBookNo(Long userNo, Long bookNo) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from cart where user_no = ? and book_no = ?");) {

			pstmt.setLong(1, userNo);
			pstmt.setLong(2, bookNo);

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}
	
}
