package org.duksung.matdog_server_hanuim.mapper;

import org.apache.ibatis.annotations.*;
import org.duksung.matdog_server_hanuim.dto.Register;
import org.duksung.matdog_server_hanuim.dto.Register_lost;
import org.duksung.matdog_server_hanuim.dto.Register_spot;
import org.duksung.matdog_server_hanuim.model.RegisterRes;
import org.duksung.matdog_server_hanuim.model.dogImgUrlRes;
import java.util.List;

@Mapper
public interface RegisterMapper {
    /**
     * 보호소 공고 리스트
     * @return
     */
    @Select("SELECT * FROM register ORDER BY happenDt")
    List<RegisterRes> findAll_register();

    /**
     * 내가 쓴 공고리스트 반환
     * @return
     */
    @Select("SELECT * FROM register WHERE userIdx = #{userIdx}")
    List<Register> findAllWrite(@Param("userIdx") final int userIdx);
    @Select("SELECT * FROM register_spot WHERE userIdx = #{userIdx}")
    List<Register_spot> findAllWrite_spot(@Param("userIdx") final int userIdx);
    @Select("SELECT * FROM register_lost WHERE userIdx = #{userIdx}")
    List<Register_lost> findAllWrite_lost(@Param("userIdx") final int userIdx);

    /**
     * 내가 쓴 공고리스트 반환 By UserIdx
     * @param registerIdx
     * @return
     */
    @Select("SELECT * FROM register WHERE registerIdx = #{registerIdx}")
    RegisterRes findByRegisterIdx_write(@Param("registerIdx") final int registerIdx);
    @Select("SELECT * FROM register_spot WHERE registerIdx = #{registerIdx}")
    RegisterRes findByRegisterIdx_write_spot(@Param("registerIdx") final int registerIdx);
    @Select("SELECT * FROM register_lost WHERE registerIdx = #{registerIdx}")
    RegisterRes findByRegisterIdx_write_lost(@Param("registerIdx") final int registerIdx);

    /**
     * 모든 분양 공고 리스트 조회(나이순 정렬)
     * @return
     */
    @Select("SELECT * FROM register order by age*1 DESC")
    List<RegisterRes> findAll_age();

    /**
     * userIdx에 따른 registerIdx 조회
     * @param userIdx
     * @return
     */
    @Select("SELECT * FROM register WHERE userIdx = #{userIdx}")
    List<Register> findByuserIdx(@Param("userIdx") final int userIdx);


    /**
     * registerIdx 조회
     * @param registerIdx
     * @return
     */
    @Select("SELECT * FROM register WHERE registerIdx = #{registerIdx}")
    Register findByRegisterIdx(@Param("registerIdx") final int registerIdx);

    /**
     *
     * @param registerIdx
     * @return
     */
    @Select("SELECT * FROM register WHERE registerIdx = #{registerIdx}")
    RegisterRes findByRegisterIdx_like(@Param("registerIdx") final int registerIdx);
    @Select("SELECT * FROM register_spot WHERE registerIdx = #{registerIdx}")
    RegisterRes findByRegisterIdx_like_spot(@Param("registerIdx") final int registerIdx);
    @Select("SELECT * FROM register_lost WHERE registerIdx = #{registerIdx}")
    RegisterRes findByRegisterIdx_like_lost(@Param("registerIdx") final int registerIdx);

    /**
     * 모든 보호소 공고 보여주기
     * @param registerStatus
     * @param registerIdx
     * @return
     */
    @Select("SELECT * FROM register WHERE registerStatus = #{registerStatus} AND registerIdx = #{registerIdx}")
    Register viewAllRegister(@Param("registerStatus") final int registerStatus, @Param("registerIdx") final int registerIdx);
    @Select("SELECT popfile FROM dog_img WHERE registerStatus = #{registerStatus} AND registerIdx = #{registerIdx}")
    dogImgUrlRes viewAllRegister_img(@Param("registerStatus") final int registerStatus, @Param("registerIdx") final int registerIdx);

    @Select("SELECT * FROM register_lost WHERE registerStatus = #{registerStatus} AND registerIdx = #{registerIdx}")
    Register_lost viewAllRegister_lost(@Param("registerStatus") final int registerStatus, @Param("registerIdx") final int registerIdx);
    @Select("SELECT popfile FROM dog_img_lost WHERE registerStatus = #{registerStatus} AND registerIdx = #{registerIdx}")
    List<dogImgUrlRes> viewAllRegisterLost_img(@Param("registerStatus") final int registerStatus, @Param("registerIdx") final int registerIdx);

    /**
     * 검색_나이순
     * @param keyword
     * @return
     */
    @Select("SELECT * FROM register WHERE kindCd LIKE concat('%',#{keyword},'%') OR careAddr LIKE concat('%',#{keyword},'%') ORDER BY age DESC")
    List<RegisterRes> search_register_age(@Param("keyword") final String keyword);

    /**
     * 검색_등록일순
     * @param keyword
     * @return
     */
    @Select("SELECT * FROM register WHERE kindCd LIKE concat('%',#{keyword},'%') OR careAddr LIKE concat('%',#{keyword},'%') ORDER BY happenDt")
    List<RegisterRes> search_register_date(@Param("keyword") final String keyword);

    /**
     * 원하는 품종 리스트 검색_나이
     * @param kindCd
     * @return
     */
    @Select("SELECT * FROM register WHERE kindCd LIKE concat('%', #{kindCd}, '%') ORDER BY age")
    List<RegisterRes> findDogList_age(@Param("kindCd") final String kindCd);

    /**
     * 원하는 품종 리스트 검색_등록일순
     * @param kindCd
     * @return
     */
    @Select("SELECT * FROM register WHERE kindCd LIKE concat('%', #{kindCd}, '%') ORDER BY happenDt")
    List<RegisterRes> findDogList_date(@Param("kindCd") final String kindCd);

    /**
     * 분양 공고 등록
     * @param userIdx
     * @param re
     * @return
     */
    @Insert("INSERT INTO register(userIdx, registerStatus, kindCd, sexCd, neuterYn, weight, age, orgNm, careAddr, happenDt, specialMark, careTel, email, dm, filename) " +
            "VALUES(#{userIdx}, #{re.registerStatus}, #{re.kindCd}, #{re.sexCd}, #{re.neuterYn}, #{re.weight}, " +
            "#{re.age}, #{re.orgNm}, #{re.careAddr}, #{re.happenDt}, " +
            "#{re.specialMark}, #{re.careTel}, " +
            "#{re.email}, #{re.dm}, #{re.filename})")
    @Options(useGeneratedKeys = true, keyColumn = "registerIdx", keyProperty = "re.registerIdx")
    int save(@Param("userIdx") final int userIdx, @Param("re") final Register re);

    /**
     * 이미지 저장
     * @param userIdx
     * @param registerIdx
     * @param popfile
     * @param registerStatus
     * @return
     */
    @Insert("INSERT INTO dog_img(userIdx, registerIdx, popfile, registerStatus) VALUES(#{userIdx},#{registerIdx}, #{popfile}, #{registerStatus})")
    @Options(useGeneratedKeys = true, keyColumn = "dog_img.urlIdx")
    int save_img(@Param("userIdx") final int userIdx,@Param("registerIdx") final int registerIdx, @Param("popfile") final String popfile, @Param("registerStatus") final int registerStatus);

    /**
     * 분양 공고 수정
     * @param userIdx
     * @param registerIdx
     * @param register
     * @return
     */
    @Update("UPDATE register SET kindCd=#{register.kindCd}, sexCd=#{register.sexCd}, neuterYn=#{register.neuterYn},weight=#{register.weight},age=#{register.age}," +
            "orgNm=#{register.orgNm}, careAddr=#{register.careAddr}, specialMark=#{register.specialMark}, careTel=#{register.careTel}, email=#{register.email},dm=#{register.dm} " +
            "where userIdx = #{userIdx} AND registerIdx = #{registerIdx}")
    int update(@Param("userIdx") final int userIdx, @Param("registerIdx") final int registerIdx, @Param("register") final Register register);

    /**
     * 분양 공고 삭제
     * @param userIdx
     * @param registerIdx
     */
    @Delete("DELETE FROM register WHERE userIdx = #{userIdx} AND registerIdx = #{registerIdx}")
    void deleteRegister(@Param("userIdx") final int userIdx, @Param("registerIdx") final int registerIdx);
    @Delete("DELETE FROM dog_img WHERE userIdx = #{userIdx} AND registerIdx = #{registerIdx}")
    void deleteRegister_img(@Param("userIdx") final int userIdx, @Param("registerIdx") final int registerIdx);
    @Delete("DELETE FROM register_like WHERE userIdx = #{userIdx} AND registerIdx = #{registerIdx}")
    void deleteRegister_like(@Param("userIdx") final int userIdx, @Param("registerIdx") final int registerIdx);
}
