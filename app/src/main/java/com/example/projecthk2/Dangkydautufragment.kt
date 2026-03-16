package com.example.projecthk2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText

class DangKyDauTuFragment : BaseFragment() {

    private val kyHanOptions = listOf(
        "3 tháng", "6 tháng", "12 tháng", "24 tháng", "Không kỳ hạn"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dang_ky_dau_tu, container, false)

        // Views
        val btnBack         = view.findViewById<ImageButton>(R.id.btnBack)
        val btnXacNhan      = view.findViewById<Button>(R.id.btnXacNhan)
        val tilHoTen        = view.findViewById<TextInputLayout>(R.id.tilHoTen)
        val tilSoDienThoai  = view.findViewById<TextInputLayout>(R.id.tilSoDienThoai)
        val tilCCCD         = view.findViewById<TextInputLayout>(R.id.tilCCCD)
        val tilSoLuong      = view.findViewById<TextInputLayout>(R.id.tilSoLuong)
        val tilKyHan        = view.findViewById<TextInputLayout>(R.id.tilKyHan)
        val etHoTen         = view.findViewById<TextInputEditText>(R.id.etHoTen)
        val etSoDienThoai   = view.findViewById<TextInputEditText>(R.id.etSoDienThoai)
        val etCCCD          = view.findViewById<TextInputEditText>(R.id.etCCCD)
        val etSoLuong       = view.findViewById<TextInputEditText>(R.id.etSoLuong)
        val actvKyHan       = view.findViewById<AutoCompleteTextView>(R.id.actvKyHan)
        val chipGroup       = view.findViewById<ChipGroup>(R.id.chipGroupSoLuong)
        val cbDieuKhoan     = view.findViewById<CheckBox>(R.id.cbDieuKhoan)

        // Dropdown kỳ hạn
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            kyHanOptions
        )
        actvKyHan.setAdapter(adapter)

        // Chip chọn nhanh số lượng
        val chipAmountMap = mapOf(
            R.id.chip01 to "0.1",
            R.id.chip05 to "0.5",
            R.id.chip1  to "1",
            R.id.chip2  to "2",
            R.id.chip5  to "5",
            R.id.chip10 to "10"
        )
        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val amount = chipAmountMap[checkedIds.firstOrNull()] ?: return@setOnCheckedStateChangeListener
            etSoLuong.setText(amount)
            etSoLuong.setSelection(etSoLuong.text?.length ?: 0)
        }

        // Nút back
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Nút xác nhận
        btnXacNhan.setOnClickListener {
            val hoTen   = etHoTen.text?.toString()?.trim().orEmpty()
            val sdt     = etSoDienThoai.text?.toString()?.trim().orEmpty()
            val cccd    = etCCCD.text?.toString()?.trim().orEmpty()
            val soLuong = etSoLuong.text?.toString()?.trim().orEmpty()
            val kyHan   = actvKyHan.text?.toString()?.trim().orEmpty()

            var isValid = true

            if (hoTen.isEmpty()) {
                tilHoTen.error = "Vui lòng nhập họ và tên"
                isValid = false
            } else tilHoTen.error = null

            if (sdt.isEmpty()) {
                tilSoDienThoai.error = "Vui lòng nhập số điện thoại"
                isValid = false
            } else if (!sdt.matches(Regex("^(0[35789])[0-9]{8}$"))) {
                tilSoDienThoai.error = "Số điện thoại không hợp lệ"
                isValid = false
            } else tilSoDienThoai.error = null

            if (cccd.isEmpty() || cccd.length < 9) {
                tilCCCD.error = "CCCD/CMND phải có ít nhất 9 số"
                isValid = false
            } else tilCCCD.error = null

            val luong = soLuong.toDoubleOrNull()
            if (luong == null || luong < 0.1) {
                tilSoLuong.error = "Tối thiểu 0.1 lượng"
                isValid = false
            } else tilSoLuong.error = null

            if (kyHan.isEmpty() || kyHan == "-- Chọn kỳ hạn --") {
                tilKyHan.error = "Vui lòng chọn kỳ hạn"
                isValid = false
            } else tilKyHan.error = null

            if (!cbDieuKhoan.isChecked) {
                Toast.makeText(requireContext(), "Vui lòng đồng ý với điều khoản dịch vụ", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            if (!isValid) return@setOnClickListener

            // Mở trang thành công
            val fragment = DangKyThanhCongFragment.newInstance(
                hoTen   = hoTen,
                soLuong = "$soLuong lượng",
                kyHan   = kyHan
            )
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack("ThanhCong")
                .commit()
        }

        return view
    }
}