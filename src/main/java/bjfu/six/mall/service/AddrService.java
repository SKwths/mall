package bjfu.six.mall.service;

import bjfu.six.mall.entity.po.Address;
import bjfu.six.mall.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddrService {
    @Autowired
    private AddressMapper addressMapper;

    public Address[] saveAddr(String name, String mobile, String province, String city, String district, String addr, String zip, int user_id)
    {
        Address address = new Address();
        address.setName(name);
        address.setMobile(mobile);
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(district);
        address.setAddr(addr);
        address.setZip(zip);
        address.setUser_Id(user_id);
        address.setDel_state(0);
        address.setDefault_addr(0);
        if(addressMapper.insertAddr(address)== 0) {
            throw new RuntimeException("增加地址失败");
        }
        Address ad[] = addressMapper.selectAllAddr();
        return ad;
    }

    public Address[] delAddr(int id)
    {
        addressMapper.delAddr(id);
        Address[] address = addressMapper.selectDelAddr();
        return address;
    }

    public Address[] findAddr(int user_id)
    {
        addressMapper.selectFindAddr(user_id);
        Address[] address = addressMapper.selectFindAddr(user_id);
        return address;
    }

    public Address[] setDefaultAddr(int id,int user_id)
    {
        addressMapper.setDefaultAddr1(id,user_id);

        addressMapper.setDefaultAddr2(id,user_id);

        Address[] address = addressMapper.selectFindAddr(user_id);
        return address;
    }

    public Address findAddressById(int id)
    {
        Address addr = addressMapper.selectAddrById(id);
        return addr;
    }
}
