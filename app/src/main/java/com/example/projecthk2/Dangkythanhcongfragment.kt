package com.example.projecthk2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class DangKyThanhCongFragment : BaseFragment() {

    private var hoTen: String   = ""
    private var soLuong: String = ""
    private var kyHan: String   = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            hoTen   = it.getString(ARG_HO_TEN, "")
            soLuong = it.getString(ARG_SO_LUONG, "")
            kyHan   = it.getString(ARG_KY_HAN, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dang_ky_thanh_cong, container, false)

        view.findViewById<TextView>(R.id.tvSummaryHoTen).text   = hoTen.ifEmpty { "—" }
        view.findViewById<TextView>(R.id.tvSummarySoLuong).text = soLuong.ifEmpty { "—" }
        view.findViewById<TextView>(R.id.tvSummaryKyHan).text   = kyHan.ifEmpty { "—" }

        view.findViewById<Button>(R.id.btnVeTrangChu).setOnClickListener {
            parentFragmentManager.popBackStack(
                null,
                androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

        return view
    }

    companion object {
        private const val ARG_HO_TEN   = "arg_ho_ten"
        private const val ARG_SO_LUONG = "arg_so_luong"
        private const val ARG_KY_HAN   = "arg_ky_han"

        fun newInstance(hoTen: String, soLuong: String, kyHan: String) =
            DangKyThanhCongFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_HO_TEN,   hoTen)
                    putString(ARG_SO_LUONG, soLuong)
                    putString(ARG_KY_HAN,   kyHan)
                }
            }
    }
}