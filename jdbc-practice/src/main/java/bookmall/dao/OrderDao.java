package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class OrderDao {

	public void insert(OrderVo vo) {
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("insert into orders values(null, ?, ?, ?, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
				){
			
				pstmt1.setString(1, vo.getNumber());
				pstmt1.setString(2, vo.getStatus());
				pstmt1.setInt(3, vo.getPayment());
				pstmt1.setString(4, vo.getShipping());
				pstmt1.setLong(5, vo.getUserNo());
				pstmt1.setString(6, vo.getUserName());

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

	public OrderVo findByNoAndUserNo(Long no, Long userNo) {
		OrderVo vo = new OrderVo();
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select number, status, payment, shipping, user_name from orders where no = ? and user_no = ?");
				){
				pstmt.setLong(1, no);
				pstmt.setLong(2, userNo);
				
				ResultSet rs = pstmt.executeQuery();
				
				if (rs.next()) {
					String number = rs.getString(1);
					String status = rs.getString(2);
					int payment = rs.getInt(3);
					String shipping = rs.getString(4);
					String user_name = rs.getString(5);

					vo.setNo(no);
					vo.setUserNo(userNo);
					vo.setNumber(number);
					vo.setStatus(status);
					vo.setPayment(payment);
					vo.setShipping(shipping);
					vo.setUserName(user_name);
				}
				else {
					return null;
				}
				rs.close();
				
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		return vo;
	}

	public void deleteByNo(Long no) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from orders where no = ?");) {

			pstmt.setLong(1, no);

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void insertBook(OrderBookVo vo) {
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("insert into orders_book values(?, ?, ?, ?)");
				){
			
				pstmt.setLong(1, vo.getBookNo());
				pstmt.setLong(2, vo.getOrderNo());
				pstmt.setInt(3, vo.getQuantity());
				pstmt.setInt(4, vo.getPrice());

				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
	}

	public List<OrderBookVo> findBooksByNoAndUserNo(Long orderNo, Long userNo) {
		List<OrderBookVo> result = new ArrayList<>();
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select a.quantity, a.price, a.book_no, b.title "
						+ "from orders_book a, book b, orders c "
						+ "where a.book_no = b.no and a.orders_no = c.no "
						+ "and a.orders_no = ? and c.user_no = ?");
				){
				pstmt.setLong(1, orderNo);
				pstmt.setLong(2, userNo);
				
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					int quantity = rs.getInt(1);
					int price = rs.getInt(2);
					Long bookNo = rs.getLong(3);
					String title = rs.getString(4);
					
					OrderBookVo vo = new OrderBookVo();
					vo.setOrderNo(orderNo);
					vo.setQuantity(quantity);
					vo.setPrice(price);
					vo.setBookNo(bookNo);
					vo.setBookTitle(title);
					result.add(vo);
				}
				rs.close();
				
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		return result;
	}

	public void deleteBooksByNo(Long no) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from orders_book where orders_no = ?");) {

			pstmt.setLong(1, no);

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

}
