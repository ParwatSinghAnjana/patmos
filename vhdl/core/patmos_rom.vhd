--
--    patmos_rom.vhd
--
--	generic VHDL version of ROM
--
--		DONT edit this file!
--		generated by patmos.asm.Bin2Vhdl
--

library ieee;
use ieee.std_logic_1164.all;
--use ieee.numeric_std.all;

entity patmos_rom is
port (
    address : in std_logic_vector(7 downto 0);
    q : out std_logic_vector(31 downto 0)
);
end patmos_rom;

architecture rtl of patmos_rom is

begin

process(address) begin

case address is
    when "00000000" => q <= "00000000001000010000000001000000";
    when "00000001" => q <= "00000000000000100000000000000010";
    when "00000010" => q <= "00000010100101000101000110000000";
    when "00000011" => q <= "00000000000000000000000000000001";
    when "00000100" => q <= "00000010000101101010000010000111";
    when "00000101" => q <= "00000111110000000001010110000100";
    when "00000110" => q <= "00000000000000000000000000000001";
    when "00000111" => q <= "00000010100111100101000110000001";
    when "00001000" => q <= "00000010100111100101000110000001";
    when "00001001" => q <= "00000000000001110011000000000001";
    when "00001010" => q <= "00000010100101000101000110000000";
    when "00001011" => q <= "00000000000000000000000000000001";
    when "00001100" => q <= "00000010000101100011010100000111";
    when "00001101" => q <= "00000111110000000001010110000100";
    when "00001110" => q <= "00000000000000000000000000000001";
    when "00001111" => q <= "00000010110001100111011110000001";
    when "00010000" => q <= "00000000000000000000000000000001";
    when "00010001" => q <= "00000000000001100000000000000010";
    when "00010010" => q <= "00000000000001100000000000000010";
    when "00010011" => q <= "00000000000000000000000000000001";
    when "00010100" => q <= "00000001110001100011000000000000";
    when "00010101" => q <= "00000111110000010000000000010100";
    when "00010110" => q <= "00000111100000000000000000000000";
    when "00010111" => q <= "00000010010000000000000000100100";

--    when "00010000" => q <= "00000000000000000000000000000001";
--    when "00010001" => q <= "00000000000001100000000000000010";
--    when "00010010" => q <= "00000000000001100000000000000010";
--    when "00010011" => q <= "00000000000000000000000000000001";
--    when "00010100" => q <= "00000111100000000000000000000000";
--    when "00010101" => q <= "00000010010000000000000000100100";
	 
	 
--	 	rom_unit(0) <= "00000000000000100000000000000010"; -- add r1, ".00010"
--	rom_unit(1) <= "00000010100101000101111110000000"; -- load
--    rom_unit(2) <= "00000000000000000000000000000001"; --nop
--    rom_unit(3) <= "00000010000101101010000010000111"; -- and
--    rom_unit(4) <= "00000111110000001011000010000100"; --benq
--    rom_unit(5) <= "00000000000000000000000000000001"; -- nop after branch
--    rom_unit(6) <= "00000010100111100101111110000001"; -- load data from uart to r15
--    rom_unit(7) <= "00000010100111100101111110000001"; -- load data from uart to r15
--    
--    --echo
--     rom_unit(8) <= "00000000000001110011000000000001"; -- add r3 <= r19, ".00001"
--   rom_unit(9) <= "00000000000001000000000001010000"; -- store whatever
--   rom_unit(10) <= "00000010100101000101111110000000"; -- load uart status
--   rom_unit(11) <= "00000000000000000000000000000001";-- nop (we dont have nop yet so we just use an add instead)
--   rom_unit(12) <= "00000010000101101010000110000111"; -- and r11 <=r3, r10
--   rom_unit(13) <= "00000111110000001011000110000100"; -- benq
--   rom_unit(14) <= "00000000000000000000000000000001"; --instruction after branch -- this should be nop too
--   rom_unit(15) <= "00000010111111101110011110000001"; -- write r15 in uart

   
    when others => NULL; --q <= "00000000000000000000000000000000";
end case;
end process;

end rtl;