package com.ncl.nclcustomerservice.uploadfiles;

import java.io.IOException;

import io.reactivex.annotations.NonNull;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class CountingRequestBody extends RequestBody {

    private final RequestBody delegate;
    private final Listener listener;
    private final ProgressUpdate progressUpdate;


    public CountingRequestBody(RequestBody delegate, Listener listener) {
        this.delegate = delegate;
        this.listener = listener;
        this.progressUpdate=new ProgressUpdate(0,0);
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);

        delegate.writeTo(bufferedSink);

        bufferedSink.flush();
    }

    final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;
        private long previousBytesWritten = 0;

        CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(@NonNull Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            previousBytesWritten=bytesWritten;
            bytesWritten += byteCount;
            listener.onRequestProgress(previousBytesWritten,bytesWritten, contentLength(),progressUpdate);
        }
    }

    public interface Listener {
        void onRequestProgress(long previousBytesWritten, long bytesWritten, long contentLength, ProgressUpdate progressUpdate);
    }
}
