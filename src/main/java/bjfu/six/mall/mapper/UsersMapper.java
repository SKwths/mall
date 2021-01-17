package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.po.User;
import bjfu.six.mall.entity.vo.FindUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface UsersMapper {
    @Select("select * from action_users where account = #{account}")
    User selectUser(String account);

    @Select("select * from action_users where id = #{userId}")
    User selectUserByUserId(int userId);

    @Select("select question from action_users where account = #{account}")
    String selectUserQuestionByAccount(String account);

    @Select("select asw from action_users where account = #{account}")
    String selectUserAswByAccount(String account);

    @Select("select * from action_users where account = #{account} and password = #{passwordMd5} and del = 0")
    User selectUserByAccount(String account, String passwordMd5);

    @Insert("insert into action_users(account, password, name, email, phone, question, asw, role, age, sex, del) " +
            "values(#{account}, #{password}, #{name}, #{email}, #{phone}, #{question}, #{asw}, #{role}, #{age}, #{sex}, #{del}) ")
    int insertUser(User user);

    @Update("update action_users set email=#{email},phone=#{phone},question=#{question},asw=#{asw},age=#{age},sex=#{sex} where id=#{userId}")
    int updateUserInfo(String email,String phone,String question,String asw,int age,int sex,int userId);

    @Update("update action_users set password=#{newpwdMd5} where id=#{userId} and password=#{oldpwdMd5}")
    int updateUserPwd(int userId,String oldpwdMd5,String newpwdMd5);

    @Update("update action_users set password=#{newpwdMd5} where id=#{userId}")
    int updateUserPwdByQuestion(int userId,String newpwdMd5);

    @Update("update action_users set name = #{name},account = #{account},age = #{age},phone = #{phone},email = #{email},sex = #{sex} where id=#{id}")
    int mgrUpdateUser(int id,String name,String account,int age,String phone,String email,int sex);

    @Select("select * from action_users where id = #{id}")
    User findUser(int id);

    @Update("update action_users set del=1 where id = #{id}")
    int deleteUser(int id);

    @Select("select * from action_users where del=0")
    List<FindUser> findUserList();

    @Select("select * from action_users where account = #{account} and password = #{passwordMd5} and del = 0")
    User login(String account, String passwordMd5);

}
