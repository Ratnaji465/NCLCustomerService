package com.ncl.nclcustomerservice.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.getFile
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.with
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.*
import com.ncl.nclcustomerservice.adapter.CustomSpinnerAdapter
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.databinding.ActivityCreateClientprojectBinding
import com.ncl.nclcustomerservice.databinding.ClientprojectProductRowBinding
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import java.io.File
import java.lang.Exception
import java.util.*

class CreateClientProjectActivity : NetworkChangeListenerActivity(), RetrofitResponseListener {
    private lateinit var binding: ActivityCreateClientprojectBinding
    override fun onInternetConnected() {}
    override fun onInternetDisconnected() {}
    private var wc_certificate_file: File? = null
    lateinit var divisionList: MutableList<DivisionList>
    lateinit var divisionSpinnerAdater: CustomSpinnerAdapter;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_clientproject)

        binding.apply {
            toolbar.titleText.setText("Add Client Project")
            toolbar.backButton.setOnClickListener {
                finish()
            }
            tvOANumber.text = Common.setSppanableText("* Project Name")
            tvMaterialDispatch.text = Common.setSppanableText("* Date of Material dispatch from factory")
            tvAnyShortages.text = Common.setSppanableText("* Any shortages")
            tvWorkStartDate.text = Common.setSppanableText("* Work start date")
            tvTotalSFT.text = Common.setSppanableText("* Total SFT")
            tvInstalledSFT.text = Common.setSppanableText("* Installed SFT")
            tvBalanceTobeInstall.text = Common.setSppanableText("* Balance to be installed")
            tvInstallCompDate.text = Common.setSppanableText("* Installation completed date")
            tvNoOfDaysInstall.text = Common.setSppanableText("* No of days for the installation completion")
            tvHandingOver.text = Common.setSppanableText("* Handing over done")
            tvBalanceHandingOver.text = Common.setSppanableText("* Balance handing over")
            tvWorkCompletionCertif.text = Common.setSppanableText("* Work Completion certificate")
            tvWCCReceiveDate.text = Common.setSppanableText("* Work Completion certificate received date")
            tvNoOfDaysHandingOver.text = Common.setSppanableText("* No of days for the handing over")
            tvWorkStatus.text = Common.setSppanableText("* Work Status")
            tvInstallAndHandingOver.text = Common.setSppanableText("* No of days for the installation & handing over")
            val dropDownDataReqVo = DropDownDataReqVo().apply {
                teamId = Common.getTeamUserIdFromSP(this@CreateClientProjectActivity)
            }
            RetrofitRequestController(this@CreateClientProjectActivity).sendRequest(
                    Constants.RequestNames.DROP_DOWN_LIST,
                    dropDownDataReqVo,
                    true
            )
            etMaterialDispatch.setOnClickListener {
                val mcurrentDate = Calendar.getInstance()
                val mYear = mcurrentDate[Calendar.YEAR]
                val mMonth = mcurrentDate[Calendar.MONTH]
                val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
                val mDatePicker = DatePickerDialog(this@CreateClientProjectActivity, { datepicker, selectedyear, selectedmonth, selectedday ->
                    // TODO Auto-generated method stub
                    val sel_month = selectedmonth + 1
                    var sday = selectedday.toString()
                    var smonth: String? = null
                    smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                    sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                    etMaterialDispatch.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                    etMaterialDispatch.setTag("$selectedyear-$smonth-$sday")
                }, mYear, mMonth, mDay)
                mDatePicker.show()
            }
            etWorkStartDate.setOnClickListener {
                val mcurrentDate = Calendar.getInstance()
                val mYear = mcurrentDate[Calendar.YEAR]
                val mMonth = mcurrentDate[Calendar.MONTH]
                val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
                val mDatePicker = DatePickerDialog(this@CreateClientProjectActivity, { datepicker, selectedyear, selectedmonth, selectedday ->
                    // TODO Auto-generated method stub
                    val sel_month = selectedmonth + 1
                    var sday = selectedday.toString()
                    var smonth: String? = null
                    smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                    sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                    etWorkStartDate.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                    etWorkStartDate.setTag("$selectedyear-$smonth-$sday")
                }, mYear, mMonth, mDay)
                mDatePicker.show()
            }
            etInstallCompDate.setOnClickListener {
                val mcurrentDate = Calendar.getInstance()
                val mYear = mcurrentDate[Calendar.YEAR]
                val mMonth = mcurrentDate[Calendar.MONTH]
                val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
                val mDatePicker = DatePickerDialog(this@CreateClientProjectActivity, { datepicker, selectedyear, selectedmonth, selectedday ->
                    // TODO Auto-generated method stub
                    val sel_month = selectedmonth + 1
                    var sday = selectedday.toString()
                    var smonth: String? = null
                    smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                    sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                    etInstallCompDate.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                    etInstallCompDate.setTag("$selectedyear-$smonth-$sday")
                }, mYear, mMonth, mDay)
                mDatePicker.show()
            }
            etWCCReceiveDate.setOnClickListener {
                val mcurrentDate = Calendar.getInstance()
                val mYear = mcurrentDate[Calendar.YEAR]
                val mMonth = mcurrentDate[Calendar.MONTH]
                val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
                val mDatePicker = DatePickerDialog(this@CreateClientProjectActivity, { datepicker, selectedyear, selectedmonth, selectedday ->
                    // TODO Auto-generated method stub
                    val sel_month = selectedmonth + 1
                    var sday = selectedday.toString()
                    var smonth: String? = null
                    smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                    sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                    etWCCReceiveDate.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                    etWCCReceiveDate.setTag("$selectedyear-$smonth-$sday")
                }, mYear, mMonth, mDay)
                mDatePicker.show()
            }
            etDate.setOnClickListener {
                val mcurrentDate = Calendar.getInstance()
                val mYear = mcurrentDate[Calendar.YEAR]
                val mMonth = mcurrentDate[Calendar.MONTH]
                val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
                val mDatePicker = DatePickerDialog(this@CreateClientProjectActivity, { datepicker, selectedyear, selectedmonth, selectedday ->
                    // TODO Auto-generated method stub
                    val sel_month = selectedmonth + 1
                    var sday = selectedday.toString()
                    var smonth: String? = null
                    smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                    sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                    etDate.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                    etDate.setTag("$selectedyear-$smonth-$sday")
                }, mYear, mMonth, mDay)
                mDatePicker.show()
            }
            btnWcCertificate.setOnClickListener {
                with(this@CreateClientProjectActivity)
                        .compress(1024) //Final image size will be less than 1.0 MB(Optional)
                        .maxResultSize(1080, 1080) //Final image resolution will be less than 1080 x 1080(Optional)
                        .start()
            }
//            etTotalSFT.addTextChangedListener(textWatcher)
            save.setOnClickListener {
                if (isValidate()) {

                }
            }
        }
    }


    private fun isValidate(): Boolean {
        var isFilled = true
        binding.apply {
            if (etOANumber.text?.length == 0) {
                etOANumber.requestFocus()
                etOANumber.setError("Please add Project Name")
                isFilled = false
            } else if (etMaterialDispatch.text?.length == 0) {
                etMaterialDispatch.requestFocus()
                etMaterialDispatch.setError("Please add Project Address")
                isFilled = false
            } else if (etWorkStartDate.text?.length == 0) {
                etWorkStartDate.requestFocus()
                etWorkStartDate.setError("Please add Project Name")
                isFilled = false
            } else if (etTotalSFT.text?.length == 0) {
                etTotalSFT.requestFocus()
                etTotalSFT.setError("Please add Project Address")
                isFilled = false
            } else if (etInstalledSFT.text?.length == 0) {
                etInstalledSFT.requestFocus()
                etInstalledSFT.setError("Please add Project Name")
                isFilled = false
            } else if (etBalanceTobeInstall.text?.length == 0) {
                etBalanceTobeInstall.requestFocus()
                etBalanceTobeInstall.setError("Please add Project Address")
                isFilled = false
            } else if (etInstallCompDate.text?.length == 0) {
                etInstallCompDate.requestFocus()
                etInstallCompDate.setError("Please add Project Name")
                isFilled = false
            } else if (etNoOfDaysInstall.text?.length == 0) {
                etNoOfDaysInstall.requestFocus()
                etNoOfDaysInstall.setError("Please add Project Address")
                isFilled = false
            } else if (etHandingOver.text?.length == 0) {
                etHandingOver.requestFocus()
                etHandingOver.setError("Please add Project Address")
                isFilled = false
            } else if (etBalanceHandingOver.text?.length == 0) {
                etBalanceHandingOver.requestFocus()
                etBalanceHandingOver.setError("Please add Project Name")
                isFilled = false
            } else if (etWCCReceiveDate.text?.length == 0) {
                etWCCReceiveDate.requestFocus()
                etWCCReceiveDate.setError("Please add Project Address")
                isFilled = false
            } else if (etNoOfDaysHandingOver.text?.length == 0) {
                etNoOfDaysHandingOver.requestFocus()
                etNoOfDaysHandingOver.setError("Please add Project Name")
                isFilled = false
            } else if (etInstallAndHandingOver.text?.length == 0) {
                etInstallAndHandingOver.requestFocus()
                etInstallAndHandingOver.setError("Please add Project Address")
                isFilled = false
            }
        }

        return isFilled

    }

    //    private val textWatcher = object : TextWatcher {
//        override fun afterTextChanged(s: Editable?) {
//        }
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        }
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        if(binding.etTotalSFT.text!!.length>0){
//
//        }
//        }
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val file = getFile(data)
            val firstlink1 = file!!.absolutePath.subSequence(0, file.absolutePath.lastIndexOf('/')).toString()
            wc_certificate_file = File(file.absolutePath) // Assuming it is in Internal Storage
            println("## firstlink:$firstlink1")
            binding.wcCertifFileName.setText(file.name)
        }
    }

    override fun onResponseSuccess(objectResponse: ApiResponseController, objectRequest: ApiRequestController, progressDialog: ProgressDialog) {
        try {
            when (objectResponse.requestname) {
                Constants.RequestNames.DROP_DOWN_LIST -> {
                    val dropDownData: DropDownData = Common.getSpecificDataObject<DropDownData>(
                            objectResponse.result,
                            DropDownData::class.java
                    )
                    divisionList = dropDownData.divisionList
                    val dl = DivisionList()
                    dl.divisionMasterId = "0"
                    dl.divisionName = "Select"
                    divisionList.add(0, dl)
                    val states: MutableList<SpinnerModel> = ArrayList()
                    var i = 0
                    while (i < divisionList.size) {
                        val spinnerModel = SpinnerModel()
                        spinnerModel.id = divisionList[i].divisionMasterId
                        spinnerModel.title = divisionList[i].divisionName
                        states.add(spinnerModel)
                        i++
                    }



                    val rowView = layoutInflater.inflate(R.layout.clientproject_product_row, null)
                    binding.llProducts.addView(rowView)
                    val clientprojectBinding: ClientprojectProductRowBinding =
                            DataBindingUtil.inflate(
                                    layoutInflater,
                                    R.layout.clientproject_product_row,
                                    null,
                                    false
                            )
                    setProductSpinner(clientprojectBinding.productSpinner,divisionList,states,clientprojectBinding)
                    clientprojectBinding.productSpinner.adapter = divisionSpinnerAdater
                    clientprojectBinding.productSpinner.setOnItemSelectedListener(object :
                            AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            divisionList.get(position).divisionName
                            Toast.makeText(this@CreateClientProjectActivity, divisionList.get(position).divisionName, Toast.LENGTH_LONG).show()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    })
                }
            }
            Common.dismissProgressDialog(progressDialog)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Common.disPlayExpection(exception, progressDialog)
        }
    }

    private fun setProductSpinner(spinner: Spinner,divisionList: List<DivisionList>,states :List<SpinnerModel>,clientprojectBinding: ClientprojectProductRowBinding) {
        divisionSpinnerAdater = CustomSpinnerAdapter(this, 0, states)
        spinner.adapter=divisionSpinnerAdater
    }
}
//class DivisionSpinnerAdater(
//        private val activity: Context,
//        textViewResourceId: Int,
//        data: MutableList<SpinnerModel>
//) : ArrayAdapter<String?>(activity, textViewResourceId, data) {
//    private val data: MutableList<SpinnerModel>
//    var res: Resources? = null
//    var tempValues: SpinnerModel? = null
//    var inflater: LayoutInflater
//
//    override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
//        return getCustomView(position, convertView, parent)
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        return getCustomView(position, convertView, parent)
//    }
//    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val row = inflater.inflate(R.layout.spinner_rows, parent, false)
//        tempValues = null
//        tempValues = data[position]
//        val label = row.findViewById<View>(R.id.spinnertitle) as TextView
//        label.text = tempValues!!.title
//        label.tag = tempValues!!.id
//        return row
//    }
//    init {
//        this.data = data
//        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//    }
//}