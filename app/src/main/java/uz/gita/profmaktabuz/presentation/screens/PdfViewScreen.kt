package uz.gita.profmaktabuz.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.profmaktabuz.R
import uz.gita.profmaktabuz.databinding.ScreenPdfViewerBinding
import java.io.File

@AndroidEntryPoint
class PdfViewScreen : Fragment(R.layout.screen_pdf_viewer) {
    private val binding by viewBinding(ScreenPdfViewerBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backButton.setOnClickListener { findNavController().navigateUp() }
        val pdfView = binding.pdfView
        val path = "${activity?.filesDir?.absolutePath}/prof.pdf"
        pdfView.fromFile(File(path)).load()
    }
}