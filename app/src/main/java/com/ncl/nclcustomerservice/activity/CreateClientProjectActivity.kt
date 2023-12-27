package com.ncl.nclcustomerservice.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import butterknife.BindView
import butterknife.ButterKnife
import com.github.dhaval2404.imagepicker.ImagePicker.Companion
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.with
import com.google.gson.Gson
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner
import com.kenmeidearu.searchablespinnerlibrary.mListString
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.*
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity
import com.ncl.nclcustomerservice.activity.CreateNewContactActivity.RemarksViewHolder
import com.ncl.nclcustomerservice.application.MyApplication
import com.ncl.nclcustomerservice.commonutils.*
import com.ncl.nclcustomerservice.commonutils.Constants.RequestNames.ADD_CLIENT_PROJECT
import com.ncl.nclcustomerservice.commonutils.Constants.RequestNames.EDIT_CLIENT_PROJECT
import com.ncl.nclcustomerservice.customviews.CustomEditText
import com.ncl.nclcustomerservice.databinding.ActivityCreateClientprojectBinding
import com.ncl.nclcustomerservice.databinding.ClientprojectInstallationRowBinding
import com.ncl.nclcustomerservice.databinding.ClientprojectProduct1RowBinding
import com.ncl.nclcustomerservice.databinding.ClientprojectProductRowBinding
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.first
import kotlin.collections.forEach
import kotlin.collections.forEachIndexed
import kotlin.collections.indexOfFirst
import kotlin.collections.indices
import kotlin.collections.linkedSetOf
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.toMutableList
import kotlin.collections.toTypedArray

class CreateClientProjectActivity : NetworkChangeListenerActivity(), RetrofitResponseListener {
    private var selectedProducts: LinkedHashSet<Int> = linkedSetOf<Int>()
    private var selectedSubseries: MutableMap<String, MutableList<Int>> = mutableMapOf()
    private var hmProducts: MutableMap<String, List<ProductList>> = mutableMapOf()
    private var products: MutableList<DivisionList> = mutableListOf()
    private var subseriesList: MutableList<ProductList> = mutableListOf()
    private var customerProjectIdValue: String = ""
    private var customerprojectClientProjectDetailsId: String = ""

    private lateinit var binding: ActivityCreateClientprojectBinding
    private lateinit var bindingrow: ClientprojectProduct1RowBinding

    //    private lateinit var bindingInstallPeriod: ClientprojectInstallationRowBinding
    override fun onInternetConnected() {}
    override fun onInternetDisconnected() {}
    private var wc_certificate_file: File? = null

    private var progressD: ProgressDialog? = null
    private var isEdit = false
    private var sftTotalCal: Int = 0;
    private var sftInstalledTotalCal: Int = 0;
    private var anyShortageselected: Boolean = false
    private var wcCertificateselected: Boolean = false


    private var subSeriesIds: String = ""
    private var divisionIds: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_clientproject)
        binding.apply {
            toolbar.titleText.setText("Add Client Project")
            toolbar.backButton.setOnClickListener {
                finish()
            }
            getArguments<Args>()?.let {
                customerProjectIdValue = it.customerProjectId
                isEdit = it.isEdit
                Log.d("customerProjectIdValue", customerProjectIdValue);
            }
            if (isEdit) {
                toolbar.titleText.setText("Edit Client Project")
            } else {
                setProducts(ProductAndSubseries())
                addInstallationUI(InstalledSftDetails())
            }
            tvOANumber.text = Common.setSppanableText("* OA Number")
            tvMaterialDispatch.text =
                    Common.setSppanableText("* Date of Material dispatch from factory")
//            tvWorkStartDate.text = Common.setSppanableText("* Work start date")
            tvTotalSFT.text = Common.setSppanableText("* Total SFT")
            tvInstalledSFT.text = Common.setSppanableText("* Installed SFT")
            tvBalanceTobeInstall.text = Common.setSppanableText("* Balance to be installed")
//            tvWCCReceiveDate.text =
//                Common.setSppanableText("* Work Completion certificate received date")
            tvWorkStatus.text = Common.setSppanableText("* Work Status")
//            tvShortagesMaterialReceivedon.text =
//                    Common.setSppanableText("* Shortage Material Receivedon")
            tvRemarkDate.text =
                    Common.setSppanableText("* Remark Date")
            tvClientRemarks.text =
                    Common.setSppanableText("* Remark")

            val rowCCRemarks = layoutInflater.inflate(R.layout.remarks_row, null)
            llRemarks.addView(rowCCRemarks)
            addRemarks(listOf())

//            set today date
            etRemarkDate.setText(SimpleDateFormat("yyyy-MM-dd").format(Date()))

            val dropDownDataReqVo = DropDownDataReqVo().apply {
                teamId = Common.getTeamUserIdFromSP(this@CreateClientProjectActivity)
            }
            RetrofitRequestController(this@CreateClientProjectActivity).sendRequest(
                    Constants.RequestNames.DROP_DOWN_LIST,
                    dropDownDataReqVo,
                    true
            )
            setClickListeners()
        }
    }

    private fun addRemarks(remarksList: List<RemarksListVO>) {
        binding.apply {
            for (i in 0 until llRemarks.getChildCount()) {
                val llRemarksView: View = llRemarks.getChildAt(i)
                val viewHolder = RemarksViewHolder(llRemarksView)
                viewHolder.etDate.visibility = View.VISIBLE
                viewHolder.etDate.setText(SimpleDateFormat("yyyy-MM-dd").format(Date()))
                if (llRemarks.getChildCount() > 1) {
                    viewHolder.removelayout_remarks.visibility = View.VISIBLE
                } else {
                    viewHolder.removelayout_remarks.visibility = View.GONE
                }
                if (isEdit) {
                    if (remarksList.size > i) {
                        viewHolder.etRemarks.setText(remarksList.get(i).remark)
                    }
                }
                viewHolder.removelayout_remarks.setOnClickListener {
                    llRemarks.removeViewAt(i)
                    addRemarks(listOf())
                }
                viewHolder.addlayout_remarks.setOnClickListener {
                    llRemarks.addView(layoutInflater.inflate(R.layout.remarks_row, null))
                }
            }
        }

    }
//    etTotalSFT,etInstalledSFT,etBalanceTobeInstall,etNoOfDaysInstall,etHandingOver,etBalanceHandingOver

    private fun setClickListeners() {
        binding.apply {
            getInstallationUI()
            etMaterialDispatch.setOnClickListener {
                val mcurrentDate = Calendar.getInstance()
                val mYear = mcurrentDate[Calendar.YEAR]
                val mMonth = mcurrentDate[Calendar.MONTH]
                val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
                val mDatePicker = DatePickerDialog(
                        this@CreateClientProjectActivity,
                        { datepicker, selectedyear, selectedmonth, selectedday ->
                            // TODO Auto-generated method stub
                            val sel_month = selectedmonth + 1
                            var sday = selectedday.toString()
                            var smonth: String? = null
                            smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                            sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                            etMaterialDispatch.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                            etMaterialDispatch.setTag("$selectedyear-$smonth-$sday")
                        },
                        mYear,
                        mMonth,
                        mDay
                )
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show()
            }
            etShortagesMaterialReceivedon.setOnClickListener {
                val mcurrentDate = Calendar.getInstance()
                val mYear = mcurrentDate[Calendar.YEAR]
                val mMonth = mcurrentDate[Calendar.MONTH]
                val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
                val mDatePicker = DatePickerDialog(
                        this@CreateClientProjectActivity,
                        { datepicker, selectedyear, selectedmonth, selectedday ->
                            // TODO Auto-generated method stub
                            val sel_month = selectedmonth + 1
                            var sday = selectedday.toString()
                            var smonth: String? = null
                            smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                            sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                            etShortagesMaterialReceivedon.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                            etShortagesMaterialReceivedon.setTag("$selectedyear-$smonth-$sday")
                        },
                        mYear,
                        mMonth,
                        mDay
                )
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show()
            }
            etWorkStartDate.setOnClickListener {
                val mcurrentDate = Calendar.getInstance()
                val mYear = mcurrentDate[Calendar.YEAR]
                val mMonth = mcurrentDate[Calendar.MONTH]
                val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
                val mDatePicker = DatePickerDialog(
                        this@CreateClientProjectActivity,
                        { datepicker, selectedyear, selectedmonth, selectedday ->
                            // TODO Auto-generated method stub
                            val sel_month = selectedmonth + 1
                            var sday: String
                            var smonth: String? = null
                            smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                            sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                            etWorkStartDate.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                            etWorkStartDate.setTag("$selectedyear-$smonth-$sday")
                        },
                        mYear,
                        mMonth,
                        mDay
                )
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show()
            }
            etInstallCompDate.setOnClickListener {
                val mcurrentDate = Calendar.getInstance()
                val mYear = mcurrentDate[Calendar.YEAR]
                val mMonth = mcurrentDate[Calendar.MONTH]
                val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
                val mDatePicker = DatePickerDialog(
                        this@CreateClientProjectActivity,
                        { datepicker, selectedyear, selectedmonth, selectedday ->
                            // TODO Auto-generated method stub
                            val sel_month = selectedmonth + 1
                            var sday = selectedday.toString()
                            var smonth: String? = null
                            smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                            sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                            etInstallCompDate.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                            etInstallCompDate.setTag("$selectedyear-$smonth-$sday")
                            if (etWorkStartDate.text.toString().length > 0 && etInstallCompDate.text.toString().length > 0) {
                                val diffDays: String = Common.getDiffenceBetweenDates(etWorkStartDate.text.toString(), etInstallCompDate.text.toString())
                                Log.d("Diffence b/w days", diffDays)
                                etNoOfDaysInstall.setText(diffDays)
                            }

                        },
                        mYear,
                        mMonth,
                        mDay
                )
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show()
            }
            etWCCReceiveDate.setOnClickListener {
                val mcurrentDate = Calendar.getInstance()
                val mYear = mcurrentDate[Calendar.YEAR]
                val mMonth = mcurrentDate[Calendar.MONTH]
                val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
                val mDatePicker = DatePickerDialog(
                        this@CreateClientProjectActivity,
                        { datepicker, selectedyear, selectedmonth, selectedday ->
                            // TODO Auto-generated method stub
                            val sel_month = selectedmonth + 1
                            var sday = selectedday.toString()
                            var smonth: String? = null
                            smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                            sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                            etWCCReceiveDate.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                            etWCCReceiveDate.setTag("$selectedyear-$smonth-$sday")
                            if (etInstallCompDate.text.toString().length > 0 && etWCCReceiveDate.text.toString().length > 0) {
                                val diffDays: String = Common.getDiffenceBetweenDates(etInstallCompDate.text.toString(), etWCCReceiveDate.text.toString())
                                Log.d("Diffence2 b/w days", diffDays)
                                etNoOfDaysHandingOver.setText(diffDays)
                                val installAndHandlingover = etNoOfDaysInstall.text.toString().toInt() + diffDays.toInt()
                                etInstallAndHandingOver.setText("" + installAndHandlingover)
                            }
                        },
                        mYear,
                        mMonth,
                        mDay
                )
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show()
            }

            tvShortage.setOnClickListener {
                var list = listOf<String>("Yes", "No")
                MultiSelectionDialog(
                        context = this@CreateClientProjectActivity,
                        list = list,
                        mapper = { it },
                        selectedPosition = null,
                        isSingleSelection = true,
                        isSearchable = false
                ) {
                    tvShortage.setText(list[it.first()])
                    if (list[it.first()].equals("Yes")) {
                        anyShortageselected = true
                        binding.llSelectYesAnyShrt.visibility = View.VISIBLE
                    } else {
                        anyShortageselected = false
                        binding.llSelectYesAnyShrt.visibility = View.GONE
                    }
                }.show()
            }

            tvWorkCompletion.setOnClickListener {
                var list = listOf<String>("Collected", "Not Collected")
                MultiSelectionDialog(
                        context = this@CreateClientProjectActivity,
                        list = list,
                        mapper = { it },
                        selectedPosition = null,
                        isSingleSelection = true,
                        isSearchable = false
                ) {
                    tvWorkCompletion.setText(list[it.first()])
                    if (list[it.first()].equals("Collected")) {
                        wcCertificateselected = true
                        binding.llWorkcompletionVisbleDisable.visibility = View.VISIBLE
                    } else {
                        wcCertificateselected = false
                        binding.llWorkcompletionVisbleDisable.visibility = View.GONE
                    }
                }.show()
            }
            tvWorkStatusSelect.setOnClickListener {
                var list = listOf<String>(
                        "On going project",
                        "Project on hold",
                        "Completed",
                        "No work front"
                )
                MultiSelectionDialog(
                        context = this@CreateClientProjectActivity,
                        list = list,
                        mapper = { it },
                        selectedPosition = null,
                        isSingleSelection = true,
                        isSearchable = false
                ) {
                    tvWorkStatusSelect.setText(list[it.first()])
                }.show()
            }
            btnWcCertificate.setOnClickListener {
                with(this@CreateClientProjectActivity)
                        .compress(1024) //Final image size will be less than 1.0 MB(Optional)
                        .maxResultSize(
                                1080,
                                1080
                        ) //Final image resolution will be less than 1080 x 1080(Optional)
                        .start()
            }
//            btnProducts.setOnClickListener {
//                MultiSelectionDialog(
//                        context = this@CreateClientProjectActivity,
//                        list = products,
//                        mapper = { it.divisionName },
//                        selectedPosition = selectedProducts.toMutableList(),
//                        isSingleSelection = true,
//                ) {
//                    selectedProducts = LinkedHashSet<Int>(it)
//                    setProducts(/*selectedProducts, selectedSubseries*/)
//                }.show()
//            }
            save.setOnClickListener {
                if (isValidate()) {
                    if (tvShortage.text?.toString().equals("Select")) {
                        Toast.makeText(
                                this@CreateClientProjectActivity,
                                "Please Select Shortage",
                                Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    } else if (tvWorkCompletion.text?.toString().equals("Select")) {
                        Toast.makeText(
                                this@CreateClientProjectActivity,
                                "Please Select Work Completion",
                                Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    } else if (tvWorkStatusSelect.text?.toString().equals("Select")) {
                        Toast.makeText(
                                this@CreateClientProjectActivity,
                                "Please Select Work Status",
                                Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    callApi()
                }
            }
            cancel.setOnClickListener {
                finish()
            }
            etHandingOver.onTextChange {
                updateSFTUi()
            }
        }
    }


    private fun loadProductsUI() {
        bindingrow = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.clientproject_product1_row,
                null,
                false
        )
        binding.llAddProducts.addView(bindingrow.root)
        addProducts(listOf())
    }

    data class InstallationPeriod(
            var clientprojectInstalledSftId: String = "",
            var from: String = "",
            var to: String = "",
            var sqrft: Int = 0
    )

    private fun getInstallationUI(): ClientprojectInstallationRowBinding {
        return DataBindingUtil.inflate<ClientprojectInstallationRowBinding?>(
                layoutInflater,
                R.layout.clientproject_installation_row,
                null,
                false
        ).apply {
            root.setTag(R.string.app_name, InstallationPeriod())
        }
    }

    private fun addInstallationUI(installedSftDetails: InstalledSftDetails) {
        var view = getInstallationUI()
        binding.llInstallation.addView(view.root)
        if (installedSftDetails.installationPeriodFromDate.isNotEmpty()) {
            view.etFromDate.setText(installedSftDetails.installationPeriodFromDate).apply {
                view.root.getTag(R.string.app_name)?.let { tag ->
                    (tag as InstallationPeriod).clientprojectInstalledSftId = installedSftDetails.clientprojectInstalledSftId
                    tag.from = view.etFromDate.text.toString()
                    view.root.setTag(R.string.app_name, tag)
                }
            }
            view.etToDate.setText(installedSftDetails.installationPeriodToDate).apply {
                view.root.getTag(R.string.app_name)?.let { tag ->
                    (tag as InstallationPeriod).to = view.etToDate.text.toString()
                    view.root.setTag(R.string.app_name, tag)
                }
            }
            view.etSFT.setText(installedSftDetails.installationSft).apply {
                view.root.getTag(R.string.app_name)?.let { tag ->
                    (tag as InstallationPeriod).to = view.etToDate.text.toString()
                    view.root.setTag(R.string.app_name, tag)
                }
            }
        }
        view.removelayoutPr.show()
        view.removelayoutPr.setOnClickListener {
            view.root.setTag(R.string.app_name, null)
            var parent = view.root.parent as ViewGroup
            for (i in (parent.childCount - 1) downTo 0)
                if (parent.getChildAt(i).getTag(R.string.app_name) == null)
                    parent.removeViewAt(i)
            updateSFTUi()
            if (parent.childCount == 0) {
                addInstallationUI(InstalledSftDetails())
            }
        }
        view.addlayoutPr.setOnClickListener {
            if (isValidateInstallPeriod(view)) {
                addInstallationUI(InstalledSftDetails())
            }
        }
        view.etFromDate.setOnClickListener {
            openDataPicker(view.etFromDate) {
                view.root.getTag(R.string.app_name)?.let { tag ->
                    (tag as InstallationPeriod).from = view.etFromDate.text.toString()
                    view.root.setTag(R.string.app_name, tag)
                }
            }
        }
        view.etToDate.setOnClickListener {
            openDataPicker(view.etToDate) {
                view.root.getTag(R.string.app_name)?.let { tag ->
                    (tag as InstallationPeriod).to = view.etToDate.text.toString()
                    view.root.setTag(R.string.app_name, tag)
                }
            }

        }
        view.etSFT.onTextChange {
            var number = it.toString()
            if (number.isEmpty())
                number = "0"
            view.root.getTag(R.string.app_name)?.let { tag ->
                (tag as InstallationPeriod).sqrft = number.toInt()
                view.root.setTag(R.string.app_name, tag)
            }
            updateSFTUi()
        }
    }


    data class ProjectItem(
            var cpcpProductId: String = "",
            var productPos: Int = -1,
            var subSeriesPos: Int = -1,
            var sqrft: Int = 0
    )

    fun getNewItem(): ClientprojectProductRowBinding {
        return DataBindingUtil.inflate<ClientprojectProductRowBinding?>(
                layoutInflater,
                R.layout.clientproject_product_row,
                null,
                false
        ).apply {
            root.setTag(R.string.app_name, ProjectItem())
        }
    }

    fun setProducts(productsEdit: ProductAndSubseries) {
        var view = getNewItem()
        binding.llProducts.addView(view.root)
        view.tvProducts.text = "Select"
        view.llRemove.show()
        if (productsEdit.divisionMasterId.isNotEmpty()) {
            for (i in products.indices)
                if (products[i].divisionMasterId.contains(productsEdit.divisionMasterId)) {
                    val selectedProduct = products[i]
                    view.root.getTag(R.string.app_name)?.let { tag ->
                        (tag as ProjectItem).cpcpProductId = productsEdit.customerProjectClientProjectProductsId
                        tag.productPos = i
                        view.root.setTag(R.string.app_name, tag)
                    }
                    view.tvProducts.setText(selectedProduct.divisionName)

                    for (j in products[i].productList.indices)
                        if (products[i].productList[j].productId.contains(productsEdit.subSeriesId)) {
                            val selectedPosition = j
                            view.root.getTag(R.string.app_name)?.let { tag ->
                                (tag as ProjectItem).subSeriesPos = selectedPosition
                                view.root.setTag(R.string.app_name, tag)
                            }
//                            selectedSubseries.put(selectedProduct.divisionMasterId, j.toMutableList())
                            view.tvSubseriesList.setText(products[i].productList[j].productName)
                            break
                        }
                }
            if (productsEdit.productSft.isNotEmpty()) {
                view.etSFT.setText(productsEdit.productSft).apply {
                    view.root.getTag(R.string.app_name)?.let { tag ->
                        (tag as ProjectItem).sqrft = productsEdit.productSft.toInt()
                        view.root.setTag(R.string.app_name, tag)
                    }
                }
                updateSFTUi()
            }

        }
        view.tvProducts.setOnClickListener {
            MultiSelectionDialog(
                    context = this@CreateClientProjectActivity,
                    list = products,
                    mapper = { it.divisionName },
                    selectedPosition = mutableListOf(),
                    isSingleSelection = true,
            ) {
                var selectedProduct = products[it.first()]
                view.root.getTag(R.string.app_name)?.let { tag ->
                    (tag as ProjectItem).productPos = it.first()
                    view.root.setTag(R.string.app_name, tag)
                }
                view.tvSubseriesList.text = "Select Subseries"
                view.etSFT.setText("")
                view.tvProducts.setText(selectedProduct.divisionName)
                view.tvSubseriesList.setOnClickListener {
                    val product =
                            products[(view.root.getTag(R.string.app_name) as ProjectItem).productPos]
                    MultiSelectionDialog(
                            context = this@CreateClientProjectActivity,
                            list = product.productList,
                            mapper = { it.productName },
                            selectedPosition = mutableListOf(),
                            isSingleSelection = true,
                    ) {
                        var selectedPosition = it.first()
                        view.root.getTag(R.string.app_name)?.let { tag ->
                            (tag as ProjectItem).subSeriesPos = selectedPosition
                            view.root.setTag(R.string.app_name, tag)
                        }
                        selectedSubseries.put(product.divisionMasterId, it.toMutableList())
                        view.tvSubseriesList.setText(product.productList[selectedPosition].productName)
                    }.show()
                }
            }.show()

        }
        view.llAdd.setOnClickListener {
            if (view.tvProducts.text == "Select") {
                toast("Please select Product")
            } else if (view.tvSubseriesList.text == "Select Subseries") {
                toast("Please select subseries")
            } else if (view.etSFT.text.toString().isEmpty())
                toast("Please enter Sft ")
            else
                setProducts(ProductAndSubseries())

        }
        view.llRemove.setOnClickListener {
            view.root.setTag(R.string.app_name, null)
            var parent = view.root.parent as ViewGroup
            for (i in (parent.childCount - 1) downTo 0)
                if (parent.getChildAt(i).getTag(R.string.app_name) == null)
                    parent.removeViewAt(i)
            updateSFTUi()
            if (parent.childCount == 0) {
                setProducts(ProductAndSubseries())
            }
        }
        view.etSFT.onTextChange {
            var number = it.toString()
            if (number.isEmpty())
                number = "0"
            view.root.getTag(R.string.app_name)?.let { tag ->
                (tag as ProjectItem).sqrft = number.toInt()
                view.root.setTag(R.string.app_name, tag)
            }
            updateSFTUi()
        }
    }

    private fun updateSFTUi() {
        var totalSqrFt = binding.llProducts.children.mapNotNull { it.getTag(R.string.app_name) }
                .map { (it as ProjectItem).sqrft }.sum()
        binding.etTotalSFT.setText(totalSqrFt.toString())
        var totalInstallationSqrFt =
                binding.llInstallation.children.mapNotNull { it.getTag(R.string.app_name) }
                        .map { (it as InstallationPeriod).sqrft }.sum()
        binding.etInstalledSFT.setText(totalInstallationSqrFt.toString())
        binding.etBalanceTobeInstall.setText((totalSqrFt - totalInstallationSqrFt).toString())
        var handlingOverDone = try {
            binding.etHandingOver.text.toString().toInt()
        } catch (e: java.lang.Exception) {
            0
        }
        binding.etBalanceHandingOver.setText((totalSqrFt - handlingOverDone).toString())
    }


    private fun openDataPicker(etFromDate: CustomEditText, onDateSelect: (String) -> Unit) {
        val mcurrentDate = Calendar.getInstance()
        val mYear = mcurrentDate[Calendar.YEAR]
        val mMonth = mcurrentDate[Calendar.MONTH]
        val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
        val mDatePicker = DatePickerDialog(
                this@CreateClientProjectActivity,
                { datepicker, selectedyear, selectedmonth, selectedday ->
                    val sel_month = selectedmonth + 1
                    var sday = selectedday.toString()
                    var smonth: String? = null
                    smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                    sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                    etFromDate.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                    etFromDate.setTag("$selectedyear-$smonth-$sday")
                    onDateSelect("$selectedyear-$smonth-$sday")
                },
                mYear,
                mMonth,
                mDay
        )
        mDatePicker.show()
    }


    private fun addProducts(productsEdit: List<ProductAndSubseries>) {
        for (i in 0 until binding.llAddProducts.getChildCount()) {
            if (binding.llAddProducts.getChildCount() > 1) {
                bindingrow.removelayoutPr.visibility = View.VISIBLE
            } else {
                bindingrow.removelayoutPr.visibility = View.GONE
            }
            bindingrow.removelayoutPr.setOnClickListener {
                binding.llAddProducts.removeViewAt(i)
                addProducts(listOf())
            }
            bindingrow.addlayoutPr.setOnClickListener {
                if (validateForAdd()) {
                    val productAndSubseries = ProductAndSubseries()
                    productAndSubseries.divisionId = divisionIds
                    productAndSubseries.subSeriesId = subSeriesIds
                    productAndSubseries.productSft = bindingrow.etSFT.text.toString()
                    sftTotalCal += Integer.parseInt(bindingrow.etSFT.text.toString())
                    binding.etTotalSFT.setText(Integer.toString(sftTotalCal))
                    bindingrow = DataBindingUtil.inflate(
                            layoutInflater,
                            R.layout.clientproject_product1_row,
                            null,
                            false
                    )
                    binding.llAddProducts.addView(bindingrow.root)
                    addProducts(listOf())
                }
            }
            val productList = ArrayList<mListString>()
            products.map { mListString(it.divisionMasterId.toInt(), it.divisionName) }
            for (j in products.indices) {
                productList.add(
                        mListString(
                                products.get(j).divisionMasterId.toInt(),
                                products.get(j).divisionName
                        )
                )
            }
            bindingrow.tvProducts.setAdapter(productList, 1, 1)
            bindingrow.tvProducts.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                        adapterView: AdapterView<*>?,
                        view: View,
                        i: Int,
                        l: Long
                ) {
                    subseriesList.clear()
                    if (i > 0) {
                        divisionIds = products[i].divisionMasterId
                        subseriesList.addAll(products[i].productList)
                        loadSubseries(subseriesList)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
            var instantTotal: Int
            bindingrow.etSFT.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!bindingrow.etSFT.text.toString().isEmpty()) {
                        instantTotal =
                                sftTotalCal + Integer.parseInt(bindingrow.etSFT.text.toString())
                        binding.etTotalSFT.setText(Integer.toString(instantTotal))
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })


        }
    }


    private fun isValidateInstallPeriod(bindingInstallPeriod: ClientprojectInstallationRowBinding): Boolean {
        var isFilled = true
        bindingInstallPeriod.apply {
            if (etFromDate.text?.length == 0) {
                etFromDate.requestFocus()
                toast("Please add From Date")
                isFilled = false
            } else if (etToDate.text?.length == 0) {
                etToDate.requestFocus()
                toast("Please add To Date")
                isFilled = false
            } else if (etSFT.text?.length == 0) {
                etSFT.requestFocus()
                toast("Please add SFT")
                isFilled = false
            }
        }
        return isFilled
    }

    private fun validateForAdd(): Boolean {
        var isFilled = true
        bindingrow.apply {
            if (tvProducts.selectedItemPosition == 0) {
                Toast.makeText(
                        this@CreateClientProjectActivity,
                        "Please select product",
                        Toast.LENGTH_SHORT
                ).show()
                isFilled = false
            } else if (tvSubseriesList.selectedItemPosition == 0) {
                Toast.makeText(
                        this@CreateClientProjectActivity,
                        "Please select Subseries",
                        Toast.LENGTH_SHORT
                ).show()
                isFilled = false
            } else if (etSFT.text?.length == 0) {
                etSFT.requestFocus()
                etSFT.setError("Please add SFT")
                isFilled = false
            }
        }
        return isFilled
    }

    private fun loadSubseries(subseriesList: List<ProductList>) {
        val subsList = ArrayList<mListString>()
        for (k in subseriesList.indices) {
            subsList.add(
                    mListString(
                            subseriesList[k].productId.toInt(),
                            subseriesList[k].productName
                    )
            )
        }
        bindingrow.tvSubseriesList.setAdapter(subsList, 1, 1)
        bindingrow.tvSubseriesList.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long
            ) {
                if (i > 0) {
                    subSeriesIds = subseriesList[i].productId
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }


    private fun callApi() {
        try {
            val productList: MutableList<ProductAndSubseries> = mutableListOf()
            val installedSftDetails: MutableList<InstalledSftDetails> = mutableListOf()
            var customerProjectClientProjProId: String = ""
            binding.llProducts.children.forEach {
                it.getTag(R.string.app_name)?.let {
                    val project = (it as ProjectItem)
                    if (project.productPos != -1 && project.subSeriesPos != -1) {
                        if (isEdit) {
                            customerProjectClientProjProId = project.cpcpProductId
                        }
                        val productId = products[project.productPos].divisionMasterId
                        val subSeriesId =
                                products[project.productPos].productList[project.subSeriesPos].productId
                        println("productId : $productId , subseries : $subSeriesId  sqrtft: ${project.sqrft}")
                        productList.add(ProductAndSubseries().apply {
                            if (isEdit) {
                                this.customerProjectClientProjectProductsId = customerProjectClientProjProId
                            }
                            this.divisionId = productId
                            this.subSeriesId = subSeriesId
                            this.productSft = project.sqrft.toString()
                        })
                    }
                }
            }
            binding.llInstallation.children.forEach {
                it.getTag(R.string.app_name)?.let {
                    var installationPeriod = (it as InstallationPeriod)
                    if (installationPeriod.from.isNotEmpty() && installationPeriod.to.isNotEmpty()) {
                        installedSftDetails.add(
                                InstalledSftDetails(
                                        clientprojectInstalledSftId = installationPeriod.clientprojectInstalledSftId,
                                        installationPeriodFromDate = installationPeriod.from,
                                        installationPeriodToDate = installationPeriod.to,
                                        installationSft = installationPeriod.sqrft.toString()
                                )
                        )
                    }
                }
            }

            val request = ClientProject(
                    requestName = ADD_CLIENT_PROJECT,
                    requesterId = "" + Common.getUserIdFromSP(this@CreateClientProjectActivity),
                    customerProjectId = customerProjectIdValue,
                    oaNumber = binding.etOANumber.text.toString(),
                    materialDispatchDate = binding.etMaterialDispatch.text.toString(),
                    anyShortage = when (anyShortageselected) {
                        true -> "YES"
                        false -> "NO"
                    },
                    shortageRemarks = binding.etClientRemarks.text.toString(),
                    remarkDate = binding.etRemarkDate.text.toString(),
                    shortageMaterialReceived = binding.etShortagesMaterialReceivedon.text.toString(),
                    workStartDate = binding.etWorkStartDate.text.toString(),
                    totalSft = binding.etTotalSFT.text.toString(),
                    installedSft = binding.etInstalledSFT.text.toString(),
                    balanceToInstall = binding.etBalanceTobeInstall.text.toString(),
                    installationCompletionDate = binding.etInstallCompDate.text.toString(),
                    noOfDaysForInstallation = binding.etNoOfDaysInstall.text.toString(),
                    handingOverDone = binding.etHandingOver.text.toString(),
                    balanceHandingOver = binding.etBalanceHandingOver.text.toString(),
                    workCompletionCertificateImagePath = "",
                    workCompletionCertificate = binding.tvWorkCompletion.text.toString(),
                    workCompletionCertificateReceivedDate = binding.etWCCReceiveDate.text.toString(),
                    noOfDaysForHandingOver = binding.etNoOfDaysHandingOver.text.toString(),
                    workStatus = binding.tvWorkStatusSelect.text.toString(),
                    noOfDaysForInstallationAndHandingOver = binding.etInstallAndHandingOver.text.toString(),
//                clientProjectDate = binding.etDate.text.toString(),
                    remarksList = getRemarks(),
                    products = productList,
                    installedSftDetails = installedSftDetails

            )
            if (isEdit) {
                request.requestName = EDIT_CLIENT_PROJECT
                request.csCustomerprojectClientProjectDetailsId =
                        customerprojectClientProjectDetailsId
            }
            uploadImage(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getRemarks(): List<RemarksListVO> {
        val remarksList: MutableList<RemarksListVO> = mutableListOf()
        binding.apply {
            for (i in 0 until llRemarks.getChildCount()) {
                val childView: View = llRemarks.getChildAt(i)
                val loopHolder = RemarksViewHolder(childView)
                val remarksListVO = RemarksListVO()
//                if (isEdit) {
//                    if (contactContractorList.remarksListVOS.size > i) {
//                        remarksListVO.id = contactContractorList.remarksListVOS.get(i).id
//                    }
//                }
                if (loopHolder.etRemarks.text.toString().length > 0) {
                    remarksListVO.remark = loopHolder.etRemarks.text.toString()
                    remarksListVO.remarkDate = loopHolder.etDate.text.toString()
                    remarksList.add(remarksListVO)
                }
            }
        }

        return remarksList
    }


    private fun uploadImage(request: ClientProject) {
        val muPartList: MutableList<MultipartBody.Part> = ArrayList()
        if (wc_certificate_file != null && wc_certificate_file!!.length() > 5) {
            muPartList.add(
                    prepareFilePart(
                            "work_completion_certificate_image_path[]",
                            Uri.fromFile(wc_certificate_file),
                            wc_certificate_file!!
                    )!!
            )
        }
        Common.Log.i("Request obj " + request.toString())
//        if (muPartList.size > 0) {
        progressD = ProgressDialog(this)
        progressD!!.setMessage("Please Wait....")
        progressD!!.setCancelable(false)
        progressD!!.show()

        val fileParts = muPartList.toTypedArray()
        val abc = MyApplication.getInstance().apiInterface.uploadPaymentCollection(
                Constants.API,
                fileParts,
                request
        )
        abc.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
            ) {
                if (response.body() == null) {
                    Toast.makeText(
                            this@CreateClientProjectActivity,
                            "Intenal Server Error",
                            Toast.LENGTH_SHORT
                    ).show()
                    progressD!!.dismiss()
                    return
                }
                Toast.makeText(
                        this@CreateClientProjectActivity,
                        "New contact inserted successfully.",
                        Toast.LENGTH_SHORT
                ).show()
                var apiResponseController: ApiResponseController? = null
                try {
                    apiResponseController = Gson().fromJson(
                            response.body()!!.string(),
                            ApiResponseController::class.java
                    )
                    val clientProjectResVO = Common.getSpecificDataObject(
                            apiResponseController.result,
                            ClientProjectResponse::class.java
                    )
                    setResult(
                            Activity.RESULT_OK,
                            Intent().apply { putExtra("args", clientProjectResVO) })

                    if (clientProjectResVO != null) {
                        val clientProjectList: List<ClientProject> =
                                clientProjectResVO.clientProjects
                        if (clientProjectList.size > 0) {
                            Common.Log.i(clientProjectList.toString())
//                                db.commonDao().insertContractorContact(contactContractorList)
//                                val intent = Intent(this@CreateNewContactActivity, NewContactViewActivity::class.java)
//                                intent.putExtra("contactContractorList", contactContractorList[0])
//                                intent.putExtra("type", "Contractor")
//                                startActivity(intent)
                        }
                    }
                    finish()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                progressD!!.dismiss()
                finish()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                progressD!!.dismiss()
            }
        })
//        }
    }

    private fun prepareFilePart(file_i: String, uri: Uri, file: File): MultipartBody.Part? {
        val requestFile = RequestBody.create(
                MediaType.parse(getMimeType(this@CreateClientProjectActivity, uri)),
                file
        )

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(file_i, file.name, requestFile)
    }

    private fun getMimeType(context: CreateClientProjectActivity, uri: Uri): String? {
        val extension: String?
        //Check uri format to avoid null
        extension = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }


    private fun isValidate(): Boolean {
        var isFilled = true
        binding.apply {
            if (etOANumber.text?.length == 0) {
                etOANumber.requestFocus()
                etOANumber.setError("Please add OA Number")
                isFilled = false
            } else if (etMaterialDispatch.text?.length == 0) {
                Toast.makeText(
                        this@CreateClientProjectActivity,
                        "Please add Date of Material Dispatch",
                        Toast.LENGTH_SHORT
                ).show()
                isFilled = false
            }
//            else if (etWorkStartDate.text?.length == 0) {
//                Toast.makeText(
//                        this@CreateClientProjectActivity,
//                        "Please add Work Start Date",
//                        Toast.LENGTH_SHORT
//                ).show()
//                isFilled = false
//            }
            else if (etTotalSFT.text?.length == 0) {
                Toast.makeText(
                        this@CreateClientProjectActivity,
                        "Please add Total SFT",
                        Toast.LENGTH_SHORT
                ).show()
                isFilled = false
            } else if (etInstalledSFT.text?.length == 0) {
                Toast.makeText(
                        this@CreateClientProjectActivity,
                        "Please add Installed SFT",
                        Toast.LENGTH_SHORT
                ).show()
                isFilled = false
            } else if (etBalanceTobeInstall.text?.length == 0) {
                etBalanceTobeInstall.requestFocus()
                etBalanceTobeInstall.setError("Please add Balance to be installed")
                isFilled = false
            }
//            else if (etWCCReceiveDate.text?.length == 0) {
//                Toast.makeText(
//                    this@CreateClientProjectActivity,
//                    "Please add Work completion date",
//                    Toast.LENGTH_SHORT
//                ).show()
//                isFilled = false
//            }
            else if (anyShortageselected) {
//                if (etShortagesMaterialReceivedon.text?.length == 0) {
//                    Toast.makeText(
//                            this@CreateClientProjectActivity,
//                            "Please add Shortage Material Received",
//                            Toast.LENGTH_SHORT
//                    ).show()
//                    isFilled = false
//                } else
                    if (etClientRemarks.text?.length == 0) {
                    etClientRemarks.requestFocus()
                    etClientRemarks.setError("Please add Shortage Remark")
                    isFilled = false
                }
            }
        }
        return isFilled
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
//            val file = getFile(data)
            val file = File(data.data?.path ?: "")
            val firstlink1 =
                    file.absolutePath.subSequence(0, file.absolutePath.lastIndexOf('/'))
                            .toString()
            wc_certificate_file = File(file.absolutePath) // Assuming it is in Internal Storage
            println("## firstlink:$firstlink1")
            binding.wcCertifFileName.setText(file.name)
        }
    }

    override fun onResponseSuccess(
            objectResponse: ApiResponseController,
            objectRequest: ApiRequestController,
            progressDialog: ProgressDialog
    ) {
        try {
            when (objectResponse.requestname) {
                Constants.RequestNames.DROP_DOWN_LIST -> {
                    val dropDownData: DropDownData = Common.getSpecificDataObject<DropDownData>(
                            objectResponse.result,
                            DropDownData::class.java
                    )
                    products = dropDownData.divisionList
//                    loadProductsUI()
                }
            }
            var arrProjectId = LinkedHashSet<String>()
            products.forEach {
                arrProjectId.add(it.divisionMasterId)
                hmProducts.put(it.divisionMasterId, it.productList ?: listOf())
            }

            getArguments<Args>()?.let {
                isEdit = it.isEdit
                customerProjectIdValue = it.customerProjectId
                customerprojectClientProjectDetailsId =
                        it.clientProject!!.csCustomerprojectClientProjectDetailsId
                it.clientProject?.products?.forEachIndexed { pos, client ->
                    val key = client.divisionMasterId
                    if (arrProjectId.contains(key)) {
                        var productPosition =
                                products.indexOfFirst { it.divisionMasterId == key }
                        if (productPosition >= 0)
                            selectedProducts.add(productPosition)

                        var list = selectedSubseries[key]
                        if (list == null)
                            list = mutableListOf()
                        selectedSubseries[key] = list
                        var subSeriesPosition =
                                hmProducts[key]?.indexOfFirst { it.productCode == client.productCode }
                        subSeriesPosition?.let { list.add(it) }
                    }
                }
//                setProducts(/*selectedProducts, selectedSubseries*/)
                it.clientProject?.let { setUI(it) }
            }
            Common.dismissProgressDialog(progressDialog)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Common.disPlayExpection(exception, progressDialog)
        }
    }

    private fun setUI(clientProject: ClientProject) {
        binding.apply {
            etOANumber.setText(clientProject.oaNumber)
            etMaterialDispatch.setText(clientProject.materialDispatchDate)
            tvShortage.setText(clientProject.anyShortage)
            if (clientProject.anyShortage.equals("YES")) {
                anyShortageselected = true
                llSelectYesAnyShrt.visibility = View.VISIBLE
                etShortagesMaterialReceivedon.setText(clientProject.shortageMaterialReceived)
                etRemarkDate.setText(clientProject.remarkDate)
                etClientRemarks.setText(clientProject.shortageRemarks)
            } else {
                anyShortageselected = false
                llSelectYesAnyShrt.visibility = View.GONE
            }
            etWorkStartDate.setText(clientProject.workStartDate)
            etTotalSFT.setText(clientProject.totalSft)
            etInstalledSFT.setText(clientProject.installedSft)
            etBalanceTobeInstall.setText(clientProject.balanceToInstall)
            etInstallCompDate.setText(clientProject.installationCompletionDate)
            etHandingOver.setText(clientProject.handingOverDone)
            etBalanceHandingOver.setText(clientProject.balanceHandingOver)
            tvWorkCompletion.setText(clientProject.workCompletionCertificate)
            wcCertifFileName.setText(clientProject.workCompletionCertificateImagePath)
            tvWorkStatusSelect.setText(clientProject.workStatus)
            etNoOfDaysHandingOver.setText(clientProject.noOfDaysForHandingOver)
            etWCCReceiveDate.setText(clientProject.workCompletionCertificateReceivedDate)
            etInstallAndHandingOver.setText(clientProject.noOfDaysForInstallation)
            etNoOfDaysInstall.setText(clientProject.noOfDaysForInstallationAndHandingOver)
            etOANumber.setText(clientProject.oaNumber)
            if (clientProject.products.isNotEmpty()) {
                for (i in clientProject.products.indices) {
                    setProducts(clientProject.products.get(i))
                }
            }
            if (clientProject.installedSftDetails.isNotEmpty()) {
                for (i in clientProject.installedSftDetails.indices) {
                    addInstallationUI(clientProject.installedSftDetails.get(i))
                }
                updateSFTUi()
            }
            if (clientProject.remarksList.isNotEmpty()) {
                for (i in clientProject.remarksList.indices) {
                    val rowView = layoutInflater.inflate(R.layout.remarks_row, null)
                    llRemarks.addView(rowView)
                }
                addRemarks(clientProject.remarksList)
            }
        }

    }

    data class Args(
            var clientProject: ClientProject?,
            var customerProjectId: String,
            var isEdit: Boolean = false
    ) : Serializable

    companion object {
        fun open(context: Context, args: Args? = null) {
            context.startActivity(
                    Intent(
                            context,
                            CreateClientProjectActivity::class.java
                    ).apply {
                        putExtra("args", args)
                    })
        }

        fun launch(
                launcher: ActivityResultLauncher<Intent>,
                context: Context,
                args: Args? = null
        ) {
            launcher.launch(Intent(context, CreateClientProjectActivity::class.java).apply {
                putExtra("args", args)
            })
        }
    }

}