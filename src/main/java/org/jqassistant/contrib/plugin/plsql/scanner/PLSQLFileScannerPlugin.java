package org.jqassistant.contrib.plugin.plsql.scanner;

import java.io.IOException;
import java.io.InputStream;

import org.jqassistant.contrib.plugin.plsql.model.PLSQLFileDescriptor;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;

@Requires(FileDescriptor.class)

public class PLSQLFileScannerPlugin extends AbstractScannerPlugin<FileResource, PLSQLFileDescriptor> {

    @Override
    public boolean accepts(FileResource item, String path, Scope scope) throws IOException {
        return path.toLowerCase().endsWith(".sql");
    }

	@Override
	public PLSQLFileDescriptor scan(FileResource item, String path, Scope scope, Scanner scanner) throws IOException {

		ScannerContext context = scanner.getContext();
		final Store store = context.getStore();
		
        try (InputStream stream = item.createStream()) {
            // Retrieve the scanned file node from the scanner context.
            final FileDescriptor fileDescriptor = context.getCurrentDescriptor();
            // Add the PLSQL label.
            final PLSQLFileDescriptor plsqlFileDescriptor = store.addDescriptorType(fileDescriptor, PLSQLFileDescriptor.class);
            
            plsqlFileDescriptor.setName(item.getFile().getName());
            
            final PLSQLSourceParser sourceParser = new PLSQLSourceParser(store, plsqlFileDescriptor);
            sourceParser.parseFile(item);
            
            
            return plsqlFileDescriptor;
        }
            
	}

}
