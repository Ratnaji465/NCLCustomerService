package com.ncl.nclcustomerservice.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.databinding.DataBindingUtil
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.getFile
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.with
import com.google.gson.Gson
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.*
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity
import com.ncl.nclcustomerservice.application.MyApplication
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.commonutils.Constants.RequestNames.ADD_CLIENT_PROJECT
import com.ncl.nclcustomerservice.commonutils.Constants.RequestNames.EDIT_CLIENT_PROJECT
import com.ncl.nclcustomerservice.commonutils.getArguments
import com.ncl.nclcustomerservice.databinding.ActivityCreateClientprojectBinding
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
import java.util.*
import kotlin.collections.LinkedHashSet

class CreateClientProjectActivity : NetworkChangeListenerActivity(), RetrofitResponseListener {
    private var selectedProducts: LinkedHashSet<Int> = linkedSetOf<Int>()
    private var selectedSubseries: MutableMap<String, MutableList<Int>> = mutableMapOf()
    private var hmProducts: MutableMap<String, List<ProductList>> = mutableMapOf()
    private var products: MutableList<DivisionList> = mutableListOf()
    private var customerProjectIdValue: String = ""
    private var customerprojectClientProjectDetailsId: String = ""

    private lateinit var binding: ActivityCreateClientprojectBinding
    override fun onInternetConnected() {}
    override fun onInternetDisconnected() {}
    private var wc_certificate_file: File? = null

    private var progressD: ProgressDialog? = null
    private var isEdit = false

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
                Log.d("customerProjectIdValue",customerProjectIdValue);
            }
            if(isEdit){
                toolbar.titleText.setText("Edit Client Project")
            }
            tvOANumber.text = Common.setSppanableText("* OA Number")
            tvMaterialDispatch.text =
                Common.setSppanableText("* Date of Material dispatch from factory")
            tvAnyShortages.text = Common.setSppanableText("* Any shortages")
            tvWorkStartDate.text = Common.setSppanableText("* Work start date")
            tvTotalSFT.text = Common.setSppanableText("* Total SFT")
            tvInstalledSFT.text = Common.setSppanableText("* Installed SFT")
            tvBalanceTobeInstall.text = Common.setSppanableText("* Balance to be installed")
            tvInstallCompDate.text = Common.setSppanableText("* Installation completed date")
            tvNoOfDaysInstall.text =
                Common.setSppanableText("* No of days for the installation completion")
            tvHandingOver.text = Common.setSppanableText("* Handing over done")
            tvBalanceHandingOver.text = Common.setSppanableText("* Balance handing over")
            tvWorkCompletionCertif.text = Common.setSppanableText("* Work Completion certificate")
            tvWCCReceiveDate.text =
                Common.setSppanableText("* Work Completion certificate received date")
            tvNoOfDaysHandingOver.text =
                Common.setSppanableText("* No of days for the handing over")
            tvWorkStatus.text = Common.setSppanableText("* Work Status")
            tvInstallAndHandingOver.text =
                Common.setSppanableText("* No of days for the installation & handing over")
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

    private fun setClickListeners() {
        binding.apply {
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
                        var sday = selectedday.toString()
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
                    },
                    mYear,
                    mMonth,
                    mDay
                )
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
                    },
                    mYear,
                    mMonth,
                    mDay
                )
                mDatePicker.show()
            }
            etDate.setOnClickListener {
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
                        etDate.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                        etDate.setTag("$selectedyear-$smonth-$sday")
                    },
                    mYear,
                    mMonth,
                    mDay
                )
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
                ) {
                    tvShortage.setText(list[it.first()])
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
                ) {
                    tvWorkCompletion.setText(list[it.first()])
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
            btnProducts.setOnClickListener {
                MultiSelectionDialog(
                    context = this@CreateClientProjectActivity,
                    list = products,
                    mapper = { it.divisionName },
                    selectedPosition = selectedProducts.toMutableList(),
                    isSingleSelection = false,
                ) {
                    selectedProducts = LinkedHashSet<Int>(it)
                    setProducts(selectedProducts, selectedSubseries)
                }.show()
            }
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
        }
    }

    private fun callApi() {
        try {
            var list: MutableList<Pair<String, String>> = mutableListOf()
            selectedSubseries.forEach { hm ->
                var productCode = hm.key
                var ind = hm.value
                var subseries =
                    ind.map {
                        Pair(
                            hm.key,
                            hmProducts[productCode]?.get(it)?.productId ?: ""
                        )
                    }
                        .toMutableList()
                list.addAll(subseries.toMutableList())
            }
            var arrProduct = list.map {
                ProductAndSubseries(
                    divisionMasterId = it.first,
                    subSeriesId = it.second
                )
            }.toList()
            val request = ClientProject(
                requestName = ADD_CLIENT_PROJECT,
                requesterId = "" + Common.getUserIdFromSP(this@CreateClientProjectActivity),
                customerProjectId = customerProjectIdValue,
                oaNumber = binding.etOANumber.text.toString(),
                materialDispatchDate = binding.etMaterialDispatch.text.toString(),
                anyShortage = binding.tvShortage.text.toString(),
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
                clientProjectDate = binding.etDate.text.toString(),
                Remarks = binding.etRemarks.text.toString(),
                products = arrProduct
            )
            if(isEdit){
                request.requestName=EDIT_CLIENT_PROJECT
                request.csCustomerprojectClientProjectDetailsId=customerprojectClientProjectDetailsId
            }
            uploadImage(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun uploadImage(request: ClientProject) {
        progressD = ProgressDialog(this)
        progressD!!.setMessage("Please Wait....")
        progressD!!.setCancelable(false)
        progressD!!.show()
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
        if (muPartList.size > 0) {
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
        }
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

    fun setProducts(
        selectedProducts: LinkedHashSet<Int>,
        selectedSubseries: MutableMap<String, MutableList<Int>>
    ) {
        binding.llProducts.removeAllViews()
        selectedProducts.forEach { pos ->
            var obj = products[pos]
            var view: ClientprojectProductRowBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.clientproject_product_row,
                null,
                false
            )
            view.tvProducts.setText(obj.divisionName ?: "Select")
            view.tvSubseriesList.setText(selectedSubseries[obj.divisionMasterId]?.map { obj.productList[it].productName }
                ?.joinToString { it }
                ?: "Select Subseries")
            view.tvProducts.setOnClickListener {
                MultiSelectionDialog(
                    context = this@CreateClientProjectActivity,
                    list = products,
                    mapper = { it.divisionName },
                    selectedPosition = listOf(pos).toMutableList(),
                    isSingleSelection = true,
                ) {
                    selectedProducts.remove(pos)
                    var selectedProduct = products[it.first()]
                    selectedProducts.add(it.first())
                    view.root.setTag(R.string.app_name, selectedProduct)
                    view.tvSubseriesList.setText("Select Subseries")
                    view.tvProducts.setText(selectedProduct.divisionName)
                }.show()

            }
            view.root.setTag(R.string.app_name, obj)
            view.tvSubseriesList.setOnClickListener {
                var product = view.root.getTag(R.string.app_name) as DivisionList
                MultiSelectionDialog(
                    context = this@CreateClientProjectActivity,
                    list = product.productList,
                    mapper = { it.productName },
                    selectedPosition = selectedSubseries[product.divisionMasterId]
                        ?: mutableListOf(),
                    isSingleSelection = false,
                ) {
                    selectedSubseries.put(product.divisionMasterId, it.toMutableList())
                    view.tvSubseriesList.setText(
                        it.toMutableSet().map { product.productList[it].productName }
                            ?.joinToString { it }
                    )
                }.show()
            }
            binding.llProducts.addView(view.root)
        }
    }


    private fun isValidate(): Boolean {
        var isFilled = true
        binding.apply {
            if (etOANumber.text?.length == 0) {
                etOANumber.requestFocus()
                etOANumber.setError("Please add OA Number")
                isFilled = false
            } else if (etMaterialDispatch.text?.length == 0) {
                etMaterialDispatch.requestFocus()
                etMaterialDispatch.setError("Please add Date of Material Dispatch")
                isFilled = false
            } else if (etWorkStartDate.text?.length == 0) {
                etWorkStartDate.requestFocus()
                etWorkStartDate.setError("Please add Work Start Date")
                isFilled = false
            } else if (etTotalSFT.text?.length == 0) {
                etTotalSFT.requestFocus()
                etTotalSFT.setError("Please add Total SFT")
                isFilled = false
            } else if (etInstalledSFT.text?.length == 0) {
                etInstalledSFT.requestFocus()
                etInstalledSFT.setError("Please add Installed SFT")
                isFilled = false
            } else if (etBalanceTobeInstall.text?.length == 0) {
                etBalanceTobeInstall.requestFocus()
                etBalanceTobeInstall.setError("Please add Balance to be installed")
                isFilled = false
            } else if (etInstallCompDate.text?.length == 0) {
                etInstallCompDate.requestFocus()
                etInstallCompDate.setError("Please add Installation Completed Date")
                isFilled = false
            } else if (etNoOfDaysInstall.text?.length == 0) {
                etNoOfDaysInstall.requestFocus()
                etNoOfDaysInstall.setError("Please add No of days to Install")
                isFilled = false
            } else if (etHandingOver.text?.length == 0) {
                etHandingOver.requestFocus()
                etHandingOver.setError("Please add Handling over")
                isFilled = false
            } else if (etBalanceHandingOver.text?.length == 0) {
                etBalanceHandingOver.requestFocus()
                etBalanceHandingOver.setError("Please add Balance handing over")
                isFilled = false
            } else if (etWCCReceiveDate.text?.length == 0) {
                etWCCReceiveDate.requestFocus()
                etWCCReceiveDate.setError("Please add Work completion date")
                isFilled = false
            } else if (etNoOfDaysHandingOver.text?.length == 0) {
                etNoOfDaysHandingOver.requestFocus()
                etNoOfDaysHandingOver.setError("Please add No of days for handing over")
                isFilled = false
            } else if (etInstallAndHandingOver.text?.length == 0) {
                etInstallAndHandingOver.requestFocus()
                etInstallAndHandingOver.setError("Please add installation & handing over")
                isFilled = false
            }
        }

        return isFilled

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val file = getFile(data)
            val firstlink1 =
                file!!.absolutePath.subSequence(0, file.absolutePath.lastIndexOf('/')).toString()
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
                customerprojectClientProjectDetailsId = it.clientProject!!.csCustomerprojectClientProjectDetailsId
                it.clientProject?.products?.forEachIndexed { pos, client ->
                    val key = client.divisionMasterId
                    if (arrProjectId.contains(key)) {
                        var productPosition = products.indexOfFirst { it.divisionMasterId == key }
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
                setProducts(selectedProducts, selectedSubseries)
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
            etDate.setText(clientProject.createdDatetime)
            etOANumber.setText(clientProject.oaNumber)
            etRemarks.setText(clientProject.Remarks)
        }

    }

    data class Args(
        var clientProject: ClientProject?,
        var customerProjectId: String,
        var isEdit: Boolean = false
    ) : Serializable

    companion object {
        fun open(context: Context, args: Args? = null) {
            context.startActivity(Intent(context, CreateClientProjectActivity::class.java).apply {
                putExtra("args", args)
            })
        }

        fun launch(launcher: ActivityResultLauncher<Intent>, context: Context, args: Args? = null) {
            launcher.launch(Intent(context, CreateClientProjectActivity::class.java).apply {
                putExtra("args", args)
            })
        }
    }

}